/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.uci.ics.amber.core.tuple

import com.github.sisyphsu.dateparser.DateParserUtils

import java.sql.Timestamp
import java.text.NumberFormat
import java.util.Locale
import scala.util.Try
import scala.util.control.Exception.allCatch

object AttributeTypeUtils extends Serializable {

  /**
    * This function checks whether the current attribute in the schema matches the selected attribute for casting.
    * If it matches, its type is changed to the specified result type.
    * If it doesn't match, the original type is retained.
    * The order of attributes in the schema is preserved.
    *
    * @param schema schema of data
    * @param attribute selected attribute
    * @param resultType casting type
    * @return a new schema with the modified attribute type
    */
  def SchemaCasting(
      schema: Schema,
      attribute: String,
      resultType: AttributeType
  ): Schema = {
    val updatedAttributes = schema.getAttributes.map { attr =>
      if (attr.getName == attribute) {
        resultType match {
          case AttributeType.STRING | AttributeType.INTEGER | AttributeType.DOUBLE |
              AttributeType.LONG | AttributeType.BOOLEAN | AttributeType.TIMESTAMP |
              AttributeType.BINARY =>
            new Attribute(attribute, resultType) // Cast to the specified result type
          case AttributeType.ANY | _ =>
            attr // Retain the original type for unsupported types
        }
      } else {
        attr // Retain attributes that don't match the target
      }
    }
    Schema(updatedAttributes)
  }

  /**
    * Casts the fields of a tuple to new types according to a list of type casting units,
    * producing a new tuple that conforms to the specified type changes.
    * Each type casting unit specifies the attribute name and the target type to cast to.
    * If an attribute name in the tuple does not have a corresponding type casting unit,
    * its value is included in the result tuple without type conversion.
    *
    * @param tuple           The source tuple whose fields are to be casted.
    * @param targetTypes     A mapping of attribute names to their target types, which specifies how to cast each field.
    *                        If an attribute is not present in the map, no casting is applied to it.
    * @return                A new instance of TupleLike with fields casted to the target types
    *                        as specified by the typeCastingUnits.
    */
  def tupleCasting(
      tuple: Tuple,
      targetTypes: Map[String, AttributeType]
  ): TupleLike =
    TupleLike(
      tuple.getSchema.getAttributes.map { attr =>
        val targetType = targetTypes.getOrElse(attr.getName, attr.getType)
        parseField(tuple.getField(attr.getName), targetType, force = true)
      }
    )

  def parseFields(fields: Array[Any], schema: Schema): Array[Any] = {
    parseFields(fields, schema.getAttributes.map(attr => attr.getType).toArray)
  }

  /**
    * parse Fields to corresponding Java objects base on the given Schema AttributeTypes
    * @param attributeTypes Schema AttributeTypeList
    * @param fields fields value
    * @return parsedFields in the target AttributeTypes
    */
  @throws[AttributeTypeException]
  def parseFields(
      fields: Array[Any],
      attributeTypes: Array[AttributeType]
  ): Array[Any] = {
    fields.indices.map(i => parseField(fields(i), attributeTypes(i))).toArray
  }

  /**
    * parse Field to a corresponding Java object base on the given Schema AttributeType
    * @param field fields value
    * @param attributeType target AttributeType
    * @param force force to parse the field to the target type if possible
    *              currently only support for comma-separated numbers
    *
    * @return parsedField in the target AttributeType
    */
  @throws[AttributeTypeException]
  def parseField(
      field: Any,
      attributeType: AttributeType,
      force: Boolean = false
  ): Any = {
    if (field == null) return null
    attributeType match {
      case AttributeType.INTEGER   => parseInteger(field, force)
      case AttributeType.LONG      => parseLong(field, force)
      case AttributeType.DOUBLE    => parseDouble(field)
      case AttributeType.BOOLEAN   => parseBoolean(field)
      case AttributeType.TIMESTAMP => parseTimestamp(field)
      case AttributeType.STRING    => field.toString
      case AttributeType.BINARY    => field
      case AttributeType.ANY | _   => field
    }
  }

  @throws[AttributeTypeException]
  private def parseInteger(fieldValue: Any, force: Boolean = false): Integer = {
    val attempt: Try[Integer] = Try {
      fieldValue match {
        case str: String =>
          if (force) {
            // Use US locale for comma-separated numbers
            NumberFormat.getNumberInstance(Locale.US).parse(str.trim).intValue()
          } else {
            str.trim.toInt
          }
        case int: Integer               => int
        case long: java.lang.Long       => long.toInt
        case double: java.lang.Double   => double.toInt
        case boolean: java.lang.Boolean => if (boolean) 1 else 0
        // Timestamp and Binary are considered to be illegal here.
        case _ =>
          throw new IllegalArgumentException(
            s"Unsupported type for parsing to Integer: ${fieldValue.getClass.getName}"
          )
      }
    }

    attempt.recover {
      case e: Exception =>
        throw new AttributeTypeException(
          s"Failed to parse type ${fieldValue.getClass.getName} to Integer: ${fieldValue.toString}",
          e
        )
    }.get
  }

  @throws[AttributeTypeException]
  private def parseLong(fieldValue: Any, force: Boolean = false): java.lang.Long = {
    val attempt: Try[Long] = Try {
      fieldValue match {
        case str: String =>
          if (force) {
            // Use US locale for comma-separated numbers
            NumberFormat.getNumberInstance(Locale.US).parse(str.trim).longValue()
          } else {
            str.trim.toLong
          }
        case int: Integer               => int.toLong
        case long: java.lang.Long       => long
        case double: java.lang.Double   => double.toLong
        case boolean: java.lang.Boolean => if (boolean) 1L else 0L
        case timestamp: Timestamp       => timestamp.toInstant.toEpochMilli
        // Binary is considered to be illegal here.
        case _ =>
          throw new IllegalArgumentException(
            s"Unsupported type for parsing to Long: ${fieldValue.getClass.getName}"
          )
      }
    }
    attempt.recover {
      case e: Exception =>
        throw new AttributeTypeException(
          s"Failed to parse type ${fieldValue.getClass.getName} to Long: ${fieldValue.toString}",
          e
        )
    }.get
  }

  @throws[AttributeTypeException]
  def parseTimestamp(fieldValue: Any): Timestamp = {
    val attempt: Try[Timestamp] = Try {
      fieldValue match {
        case str: String          => new Timestamp(DateParserUtils.parseDate(str.trim).getTime)
        case long: java.lang.Long => new Timestamp(long)
        case timestamp: Timestamp => timestamp
        case date: java.util.Date => new Timestamp(date.getTime)
        // Integer, Double, Boolean, Binary are considered to be illegal here.
        case _ =>
          throw new AttributeTypeException(
            s"Unsupported type for parsing to Timestamp: ${fieldValue.getClass.getName}"
          )
      }
    }

    attempt.recover {
      case e: Exception =>
        throw new AttributeTypeException(
          s"Failed to parse type ${fieldValue.getClass.getName} to Timestamp: ${fieldValue.toString}",
          e
        )
    }.get

  }

  @throws[AttributeTypeException]
  def parseDouble(fieldValue: Any): java.lang.Double = {
    val attempt: Try[Double] = Try {
      fieldValue match {
        case str: String                => str.trim.toDouble
        case int: Integer               => int.toDouble
        case long: java.lang.Long       => long.toDouble
        case double: java.lang.Double   => double
        case boolean: java.lang.Boolean => if (boolean) 1 else 0
        // Timestamp and Binary are considered to be illegal here.
        case _ =>
          throw new AttributeTypeException(
            s"Unsupported type for parsing to Double: ${fieldValue.getClass.getName}"
          )
      }
    }

    attempt.recover {
      case e: Exception =>
        throw new AttributeTypeException(
          s"Failed to parse type ${fieldValue.getClass.getName} to Double: ${fieldValue.toString}",
          e
        )
    }.get

  }

  @throws[AttributeTypeException]
  private def parseBoolean(fieldValue: Any): java.lang.Boolean = {
    val attempt: Try[Boolean] = Try {
      fieldValue match {
        case str: String =>
          (Try(str.trim.toBoolean) orElse Try(str.trim.toInt == 1)).get
        case int: Integer               => int != 0
        case long: java.lang.Long       => long != 0
        case double: java.lang.Double   => double != 0
        case boolean: java.lang.Boolean => boolean
        // Timestamp and Binary are considered to be illegal here.
        case _ =>
          throw new AttributeTypeException(
            s"Unsupported type for parsing to Boolean: ${fieldValue.getClass.getName}"
          )
      }
    }

    attempt.recover {
      case e: Exception =>
        throw new AttributeTypeException(
          s"Failed to parse type ${fieldValue.getClass.getName} to Boolean: ${fieldValue.toString}",
          e
        )
    }.get
  }

  /**
    * Infers field types of a given row of data. The given attributeTypes will be updated
    * through each iteration of row inference, to contain the most accurate inference.
    * @param attributeTypes AttributeTypes that being passed to each iteration.
    * @param fields data fields to be parsed
    * @return
    */
  private def inferRow(
      attributeTypes: Array[AttributeType],
      fields: Array[Any]
  ): Unit = {
    for (i <- fields.indices) {
      attributeTypes.update(i, inferField(attributeTypes.apply(i), fields.apply(i)))
    }
  }

  /**
    * Infers field types of a given row of data.
    * @param fieldsIterator iterator of field arrays to be parsed.
    *                       each field array should have exact same order and length.
    * @return AttributeType array
    */
  def inferSchemaFromRows(fieldsIterator: Iterator[Array[Any]]): Array[AttributeType] = {
    var attributeTypes: Array[AttributeType] = Array()

    for (fields <- fieldsIterator) {
      if (attributeTypes.isEmpty) {
        attributeTypes = Array.fill[AttributeType](fields.length)(AttributeType.INTEGER)
      }
      inferRow(attributeTypes, fields)
    }
    attributeTypes
  }

  /**
    * infer filed type with only data field
    * @param fieldValue data field to be parsed, original as String field
    * @return inferred AttributeType
    */
  def inferField(fieldValue: Any): AttributeType = {
    tryParseInteger(fieldValue)
  }

  private def tryParseInteger(fieldValue: Any): AttributeType = {
    if (fieldValue == null)
      return AttributeType.INTEGER
    allCatch opt parseInteger(fieldValue) match {
      case Some(_) => AttributeType.INTEGER
      case None    => tryParseLong(fieldValue)
    }
  }

  private def tryParseLong(fieldValue: Any): AttributeType = {
    if (fieldValue == null)
      return AttributeType.LONG
    allCatch opt parseLong(fieldValue) match {
      case Some(_) => AttributeType.LONG
      case None    => tryParseTimestamp(fieldValue)
    }
  }

  private def tryParseTimestamp(fieldValue: Any): AttributeType = {
    if (fieldValue == null)
      return AttributeType.TIMESTAMP
    allCatch opt parseTimestamp(fieldValue) match {
      case Some(_) => AttributeType.TIMESTAMP
      case None    => tryParseDouble(fieldValue)
    }
  }

  private def tryParseDouble(fieldValue: Any): AttributeType = {
    if (fieldValue == null)
      return AttributeType.DOUBLE
    allCatch opt parseDouble(fieldValue) match {
      case Some(_) => AttributeType.DOUBLE
      case None    => tryParseBoolean(fieldValue)
    }
  }

  private def tryParseBoolean(fieldValue: Any): AttributeType = {
    if (fieldValue == null)
      return AttributeType.BOOLEAN
    allCatch opt parseBoolean(fieldValue) match {
      case Some(_) => AttributeType.BOOLEAN
      case None    => tryParseString()
    }
  }

  private def tryParseString(): AttributeType = {
    AttributeType.STRING
  }

  /**
    * InferField when get both typeSofar and tuple string
    * @param attributeType typeSofar
    * @param fieldValue data field to be parsed, original as String field
    * @return inferred AttributeType
    */
  def inferField(attributeType: AttributeType, fieldValue: Any): AttributeType = {
    attributeType match {
      case AttributeType.STRING    => tryParseString()
      case AttributeType.BOOLEAN   => tryParseBoolean(fieldValue)
      case AttributeType.DOUBLE    => tryParseDouble(fieldValue)
      case AttributeType.LONG      => tryParseLong(fieldValue)
      case AttributeType.INTEGER   => tryParseInteger(fieldValue)
      case AttributeType.TIMESTAMP => tryParseTimestamp(fieldValue)
      case AttributeType.BINARY    => tryParseString()
      case _                       => tryParseString()
    }
  }

  class AttributeTypeException(msg: String, cause: Throwable = null)
      extends IllegalArgumentException(msg, cause) {}
}

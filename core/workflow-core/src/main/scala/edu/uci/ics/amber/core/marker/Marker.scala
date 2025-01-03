package edu.uci.ics.amber.core.marker

import edu.uci.ics.amber.core.tuple.{Attribute, AttributeType, Schema, Tuple}

import scala.collection.mutable

sealed trait Marker

final case class StartOfInputChannel() extends Marker
final case class EndOfInputChannel() extends Marker

final case class State(tuple: Option[Tuple] = None, passToAllDownstream: Boolean = false)
    extends Marker {
  val data: mutable.Map[String, (AttributeType, Any)] = mutable.LinkedHashMap()
  add("passToAllDownstream", passToAllDownstream, AttributeType.BOOLEAN)
  if (tuple.isDefined) {
    tuple.get.getSchema.getAttributes.foreach { attribute =>
      add(attribute.getName, tuple.get.getField(attribute.getName), attribute.getType)
    }
  }

  def add(key: String, value: Any, valueType: AttributeType): Unit =
    data.put(key, (valueType, value))

  def get(key: String): Any = data(key)._2

  def isPassToAllDownstream: Boolean = get("passToAllDownstream").asInstanceOf[Boolean]

  def apply(key: String): Any = get(key)

  def toTuple: Tuple =
    Tuple
      .builder(
        Schema(data.map {
          case (name, (attrType, _)) =>
            new Attribute(name, attrType)
        }.toList)
      )
      .addSequentially(data.values.map(_._2).toArray)
      .build()

  override def toString: String =
    data.map { case (key, (_, value)) => s"$key: $value" }.mkString(", ")
}

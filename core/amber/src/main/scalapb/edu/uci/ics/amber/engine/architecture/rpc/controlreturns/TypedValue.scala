// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package edu.uci.ics.amber.engine.architecture.rpc.controlreturns

@SerialVersionUID(0L)
final case class TypedValue(
    expression: _root_.scala.Predef.String,
    valueRef: _root_.scala.Predef.String,
    valueStr: _root_.scala.Predef.String,
    valueType: _root_.scala.Predef.String,
    expandable: _root_.scala.Boolean
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[TypedValue] {
    @transient
    private[this] var __serializedSizeMemoized: _root_.scala.Int = 0
    private[this] def __computeSerializedSize(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = expression
        if (!__value.isEmpty) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, __value)
        }
      };
      
      {
        val __value = valueRef
        if (!__value.isEmpty) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(2, __value)
        }
      };
      
      {
        val __value = valueStr
        if (!__value.isEmpty) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(3, __value)
        }
      };
      
      {
        val __value = valueType
        if (!__value.isEmpty) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(4, __value)
        }
      };
      
      {
        val __value = expandable
        if (__value != false) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeBoolSize(5, __value)
        }
      };
      __size
    }
    override def serializedSize: _root_.scala.Int = {
      var __size = __serializedSizeMemoized
      if (__size == 0) {
        __size = __computeSerializedSize() + 1
        __serializedSizeMemoized = __size
      }
      __size - 1
      
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
      {
        val __v = expression
        if (!__v.isEmpty) {
          _output__.writeString(1, __v)
        }
      };
      {
        val __v = valueRef
        if (!__v.isEmpty) {
          _output__.writeString(2, __v)
        }
      };
      {
        val __v = valueStr
        if (!__v.isEmpty) {
          _output__.writeString(3, __v)
        }
      };
      {
        val __v = valueType
        if (!__v.isEmpty) {
          _output__.writeString(4, __v)
        }
      };
      {
        val __v = expandable
        if (__v != false) {
          _output__.writeBool(5, __v)
        }
      };
    }
    def withExpression(__v: _root_.scala.Predef.String): TypedValue = copy(expression = __v)
    def withValueRef(__v: _root_.scala.Predef.String): TypedValue = copy(valueRef = __v)
    def withValueStr(__v: _root_.scala.Predef.String): TypedValue = copy(valueStr = __v)
    def withValueType(__v: _root_.scala.Predef.String): TypedValue = copy(valueType = __v)
    def withExpandable(__v: _root_.scala.Boolean): TypedValue = copy(expandable = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = expression
          if (__t != "") __t else null
        }
        case 2 => {
          val __t = valueRef
          if (__t != "") __t else null
        }
        case 3 => {
          val __t = valueStr
          if (__t != "") __t else null
        }
        case 4 => {
          val __t = valueType
          if (__t != "") __t else null
        }
        case 5 => {
          val __t = expandable
          if (__t != false) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(expression)
        case 2 => _root_.scalapb.descriptors.PString(valueRef)
        case 3 => _root_.scalapb.descriptors.PString(valueStr)
        case 4 => _root_.scalapb.descriptors.PString(valueType)
        case 5 => _root_.scalapb.descriptors.PBoolean(expandable)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToSingleLineUnicodeString(this)
    def companion: edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue.type = edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue
    // @@protoc_insertion_point(GeneratedMessage[edu.uci.ics.amber.engine.architecture.rpc.TypedValue])
}

object TypedValue extends scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue] = this
  def parseFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue = {
    var __expression: _root_.scala.Predef.String = ""
    var __valueRef: _root_.scala.Predef.String = ""
    var __valueStr: _root_.scala.Predef.String = ""
    var __valueType: _root_.scala.Predef.String = ""
    var __expandable: _root_.scala.Boolean = false
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 10 =>
          __expression = _input__.readStringRequireUtf8()
        case 18 =>
          __valueRef = _input__.readStringRequireUtf8()
        case 26 =>
          __valueStr = _input__.readStringRequireUtf8()
        case 34 =>
          __valueType = _input__.readStringRequireUtf8()
        case 40 =>
          __expandable = _input__.readBool()
        case tag => _input__.skipField(tag)
      }
    }
    edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue(
        expression = __expression,
        valueRef = __valueRef,
        valueStr = __valueStr,
        valueType = __valueType,
        expandable = __expandable
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage eq scalaDescriptor), "FieldDescriptor does not match message type.")
      edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue(
        expression = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        valueRef = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        valueStr = __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        valueType = __fieldsMap.get(scalaDescriptor.findFieldByNumber(4).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        expandable = __fieldsMap.get(scalaDescriptor.findFieldByNumber(5).get).map(_.as[_root_.scala.Boolean]).getOrElse(false)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = ControlreturnsProto.javaDescriptor.getMessageTypes().get(10)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = ControlreturnsProto.scalaDescriptor.messages(10)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue(
    expression = "",
    valueRef = "",
    valueStr = "",
    valueType = "",
    expandable = false
  )
  implicit class TypedValueLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue](_l) {
    def expression: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.expression)((c_, f_) => c_.copy(expression = f_))
    def valueRef: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.valueRef)((c_, f_) => c_.copy(valueRef = f_))
    def valueStr: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.valueStr)((c_, f_) => c_.copy(valueStr = f_))
    def valueType: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.valueType)((c_, f_) => c_.copy(valueType = f_))
    def expandable: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Boolean] = field(_.expandable)((c_, f_) => c_.copy(expandable = f_))
  }
  final val EXPRESSION_FIELD_NUMBER = 1
  final val VALUE_REF_FIELD_NUMBER = 2
  final val VALUE_STR_FIELD_NUMBER = 3
  final val VALUE_TYPE_FIELD_NUMBER = 4
  final val EXPANDABLE_FIELD_NUMBER = 5
  def of(
    expression: _root_.scala.Predef.String,
    valueRef: _root_.scala.Predef.String,
    valueStr: _root_.scala.Predef.String,
    valueType: _root_.scala.Predef.String,
    expandable: _root_.scala.Boolean
  ): _root_.edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue = _root_.edu.uci.ics.amber.engine.architecture.rpc.controlreturns.TypedValue(
    expression,
    valueRef,
    valueStr,
    valueType,
    expandable
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[edu.uci.ics.amber.engine.architecture.rpc.TypedValue])
}

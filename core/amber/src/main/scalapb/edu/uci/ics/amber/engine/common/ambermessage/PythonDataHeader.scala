// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package edu.uci.ics.amber.engine.common.ambermessage

@SerialVersionUID(0L)
final case class PythonDataHeader(
    tag: edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity,
    payloadType: _root_.scala.Predef.String
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[PythonDataHeader] {
    @transient
    private[this] var __serializedSizeMemoized: _root_.scala.Int = 0
    private[this] def __computeSerializedSize(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = tag
        if (__value.serializedSize != 0) {
          __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
        }
      };
      
      {
        val __value = payloadType
        if (!__value.isEmpty) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(2, __value)
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
        val __v = tag
        if (__v.serializedSize != 0) {
          _output__.writeTag(1, 2)
          _output__.writeUInt32NoTag(__v.serializedSize)
          __v.writeTo(_output__)
        }
      };
      {
        val __v = payloadType
        if (!__v.isEmpty) {
          _output__.writeString(2, __v)
        }
      };
    }
    def withTag(__v: edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity): PythonDataHeader = copy(tag = __v)
    def withPayloadType(__v: _root_.scala.Predef.String): PythonDataHeader = copy(payloadType = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = tag
          if (__t != edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity.defaultInstance) __t else null
        }
        case 2 => {
          val __t = payloadType
          if (__t != "") __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => tag.toPMessage
        case 2 => _root_.scalapb.descriptors.PString(payloadType)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToSingleLineUnicodeString(this)
    def companion: edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader.type = edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader
    // @@protoc_insertion_point(GeneratedMessage[edu.uci.ics.amber.engine.common.PythonDataHeader])
}

object PythonDataHeader extends scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader] = this
  def parseFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader = {
    var __tag: _root_.scala.Option[edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity] = _root_.scala.None
    var __payloadType: _root_.scala.Predef.String = ""
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 10 =>
          __tag = _root_.scala.Some(__tag.fold(_root_.scalapb.LiteParser.readMessage[edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity](_input__))(_root_.scalapb.LiteParser.readMessage(_input__, _)))
        case 18 =>
          __payloadType = _input__.readStringRequireUtf8()
        case tag => _input__.skipField(tag)
      }
    }
    edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader(
        tag = __tag.getOrElse(edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity.defaultInstance),
        payloadType = __payloadType
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage eq scalaDescriptor), "FieldDescriptor does not match message type.")
      edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader(
        tag = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity]).getOrElse(edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity.defaultInstance),
        payloadType = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Predef.String]).getOrElse("")
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = AmbermessageProto.javaDescriptor.getMessageTypes().get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = AmbermessageProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader(
    tag = edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity.defaultInstance,
    payloadType = ""
  )
  implicit class PythonDataHeaderLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader](_l) {
    def tag: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity] = field(_.tag)((c_, f_) => c_.copy(tag = f_))
    def payloadType: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.payloadType)((c_, f_) => c_.copy(payloadType = f_))
  }
  final val TAG_FIELD_NUMBER = 1
  final val PAYLOAD_TYPE_FIELD_NUMBER = 2
  def of(
    tag: edu.uci.ics.amber.engine.common.virtualidentity.ActorVirtualIdentity,
    payloadType: _root_.scala.Predef.String
  ): _root_.edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader = _root_.edu.uci.ics.amber.engine.common.ambermessage.PythonDataHeader(
    tag,
    payloadType
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.PythonDataHeader])
}

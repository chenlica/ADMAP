// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package edu.uci.ics.amber.engine.common.actormessage

sealed trait ActorCommand extends scalapb.GeneratedSealedOneof {
  type MessageType = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage
  final def isEmpty = this.isInstanceOf[edu.uci.ics.amber.engine.common.actormessage.ActorCommand.Empty.type]
  final def isDefined = !isEmpty
  final def asMessage: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage = edu.uci.ics.amber.engine.common.actormessage.ActorCommand.ActorCommandTypeMapper.toBase(this)
  final def asNonEmpty: Option[edu.uci.ics.amber.engine.common.actormessage.ActorCommand.NonEmpty] = if (isEmpty) None else Some(this.asInstanceOf[edu.uci.ics.amber.engine.common.actormessage.ActorCommand.NonEmpty])
}

object ActorCommand {
  case object Empty extends edu.uci.ics.amber.engine.common.actormessage.ActorCommand
  
  sealed trait NonEmpty extends edu.uci.ics.amber.engine.common.actormessage.ActorCommand
  def defaultInstance: edu.uci.ics.amber.engine.common.actormessage.ActorCommand = Empty
  
  implicit val ActorCommandTypeMapper: _root_.scalapb.TypeMapper[edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage, edu.uci.ics.amber.engine.common.actormessage.ActorCommand] = new _root_.scalapb.TypeMapper[edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage, edu.uci.ics.amber.engine.common.actormessage.ActorCommand] {
    override def toCustom(__base: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage): edu.uci.ics.amber.engine.common.actormessage.ActorCommand = __base.sealedValue match {
      case __v: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Backpressure => __v.value
      case __v: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.CreditUpdate => __v.value
      case edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Empty => Empty
    }
    override def toBase(__custom: edu.uci.ics.amber.engine.common.actormessage.ActorCommand): edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage(__custom match {
      case __v: edu.uci.ics.amber.engine.common.actormessage.Backpressure => edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Backpressure(__v)
      case __v: edu.uci.ics.amber.engine.common.actormessage.CreditUpdate => edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.CreditUpdate(__v)
      case Empty => edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Empty
    })
  }
}
@SerialVersionUID(0L)
final case class ActorCommandMessage(
    sealedValue: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[ActorCommandMessage] {
    @transient
    private[this] var __serializedSizeMemoized: _root_.scala.Int = 0
    private[this] def __computeSerializedSize(): _root_.scala.Int = {
      var __size = 0
      if (sealedValue.backpressure.isDefined) {
        val __value = sealedValue.backpressure.get
        __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
      };
      if (sealedValue.creditUpdate.isDefined) {
        val __value = sealedValue.creditUpdate.get
        __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(__value.serializedSize) + __value.serializedSize
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
      sealedValue.backpressure.foreach { __v =>
        val __m = __v
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__m.serializedSize)
        __m.writeTo(_output__)
      };
      sealedValue.creditUpdate.foreach { __v =>
        val __m = __v
        _output__.writeTag(2, 2)
        _output__.writeUInt32NoTag(__m.serializedSize)
        __m.writeTo(_output__)
      };
    }
    def getBackpressure: edu.uci.ics.amber.engine.common.actormessage.Backpressure = sealedValue.backpressure.getOrElse(edu.uci.ics.amber.engine.common.actormessage.Backpressure.defaultInstance)
    def withBackpressure(__v: edu.uci.ics.amber.engine.common.actormessage.Backpressure): ActorCommandMessage = copy(sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Backpressure(__v))
    def getCreditUpdate: edu.uci.ics.amber.engine.common.actormessage.CreditUpdate = sealedValue.creditUpdate.getOrElse(edu.uci.ics.amber.engine.common.actormessage.CreditUpdate.defaultInstance)
    def withCreditUpdate(__v: edu.uci.ics.amber.engine.common.actormessage.CreditUpdate): ActorCommandMessage = copy(sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.CreditUpdate(__v))
    def clearSealedValue: ActorCommandMessage = copy(sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Empty)
    def withSealedValue(__v: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue): ActorCommandMessage = copy(sealedValue = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => sealedValue.backpressure.orNull
        case 2 => sealedValue.creditUpdate.orNull
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => sealedValue.backpressure.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
        case 2 => sealedValue.creditUpdate.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToSingleLineUnicodeString(this)
    def companion: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.type = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage
    def toActorCommand: edu.uci.ics.amber.engine.common.actormessage.ActorCommand = edu.uci.ics.amber.engine.common.actormessage.ActorCommand.ActorCommandTypeMapper.toCustom(this)
    // @@protoc_insertion_point(GeneratedMessage[edu.uci.ics.amber.engine.common.ActorCommand])
}

object ActorCommandMessage extends scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage] = this
  def parseFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage = {
    var __sealedValue: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Empty
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 10 =>
          __sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Backpressure(__sealedValue.backpressure.fold(_root_.scalapb.LiteParser.readMessage[edu.uci.ics.amber.engine.common.actormessage.Backpressure](_input__))(_root_.scalapb.LiteParser.readMessage(_input__, _)))
        case 18 =>
          __sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.CreditUpdate(__sealedValue.creditUpdate.fold(_root_.scalapb.LiteParser.readMessage[edu.uci.ics.amber.engine.common.actormessage.CreditUpdate](_input__))(_root_.scalapb.LiteParser.readMessage(_input__, _)))
        case tag => _input__.skipField(tag)
      }
    }
    edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage(
        sealedValue = __sealedValue
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage eq scalaDescriptor), "FieldDescriptor does not match message type.")
      edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage(
        sealedValue = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).flatMap(_.as[_root_.scala.Option[edu.uci.ics.amber.engine.common.actormessage.Backpressure]]).map(edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Backpressure(_))
            .orElse[edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue](__fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).flatMap(_.as[_root_.scala.Option[edu.uci.ics.amber.engine.common.actormessage.CreditUpdate]]).map(edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.CreditUpdate(_)))
            .getOrElse(edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Empty)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = ActormessageProto.javaDescriptor.getMessageTypes().get(2)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = ActormessageProto.scalaDescriptor.messages(2)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = edu.uci.ics.amber.engine.common.actormessage.Backpressure
      case 2 => __out = edu.uci.ics.amber.engine.common.actormessage.CreditUpdate
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage(
    sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Empty
  )
  sealed trait SealedValue extends _root_.scalapb.GeneratedOneof {
    def isEmpty: _root_.scala.Boolean = false
    def isDefined: _root_.scala.Boolean = true
    def isBackpressure: _root_.scala.Boolean = false
    def isCreditUpdate: _root_.scala.Boolean = false
    def backpressure: _root_.scala.Option[edu.uci.ics.amber.engine.common.actormessage.Backpressure] = _root_.scala.None
    def creditUpdate: _root_.scala.Option[edu.uci.ics.amber.engine.common.actormessage.CreditUpdate] = _root_.scala.None
  }
  object SealedValue {
    @SerialVersionUID(0L)
    case object Empty extends edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue {
      type ValueType = _root_.scala.Nothing
      override def isEmpty: _root_.scala.Boolean = true
      override def isDefined: _root_.scala.Boolean = false
      override def number: _root_.scala.Int = 0
      override def value: _root_.scala.Nothing = throw new java.util.NoSuchElementException("Empty.value")
    }
  
    @SerialVersionUID(0L)
    final case class Backpressure(value: edu.uci.ics.amber.engine.common.actormessage.Backpressure) extends edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue {
      type ValueType = edu.uci.ics.amber.engine.common.actormessage.Backpressure
      override def isBackpressure: _root_.scala.Boolean = true
      override def backpressure: _root_.scala.Option[edu.uci.ics.amber.engine.common.actormessage.Backpressure] = Some(value)
      override def number: _root_.scala.Int = 1
    }
    @SerialVersionUID(0L)
    final case class CreditUpdate(value: edu.uci.ics.amber.engine.common.actormessage.CreditUpdate) extends edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue {
      type ValueType = edu.uci.ics.amber.engine.common.actormessage.CreditUpdate
      override def isCreditUpdate: _root_.scala.Boolean = true
      override def creditUpdate: _root_.scala.Option[edu.uci.ics.amber.engine.common.actormessage.CreditUpdate] = Some(value)
      override def number: _root_.scala.Int = 2
    }
  }
  implicit class ActorCommandMessageLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage](_l) {
    def backpressure: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.Backpressure] = field(_.getBackpressure)((c_, f_) => c_.copy(sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.Backpressure(f_)))
    def creditUpdate: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.CreditUpdate] = field(_.getCreditUpdate)((c_, f_) => c_.copy(sealedValue = edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue.CreditUpdate(f_)))
    def sealedValue: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue] = field(_.sealedValue)((c_, f_) => c_.copy(sealedValue = f_))
  }
  final val BACKPRESSURE_FIELD_NUMBER = 1
  final val CREDITUPDATE_FIELD_NUMBER = 2
  def of(
    sealedValue: edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage.SealedValue
  ): _root_.edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage = _root_.edu.uci.ics.amber.engine.common.actormessage.ActorCommandMessage(
    sealedValue
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.ActorCommand])
}

@SerialVersionUID(0L)
final case class Backpressure(
    enableBackpressure: _root_.scala.Boolean
    ) extends scalapb.GeneratedMessage with edu.uci.ics.amber.engine.common.actormessage.ActorCommand.NonEmpty with scalapb.lenses.Updatable[Backpressure] {
    @transient
    private[this] var __serializedSizeMemoized: _root_.scala.Int = 0
    private[this] def __computeSerializedSize(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = enableBackpressure
        if (__value != false) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeBoolSize(1, __value)
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
        val __v = enableBackpressure
        if (__v != false) {
          _output__.writeBool(1, __v)
        }
      };
    }
    def withEnableBackpressure(__v: _root_.scala.Boolean): Backpressure = copy(enableBackpressure = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = enableBackpressure
          if (__t != false) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PBoolean(enableBackpressure)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToSingleLineUnicodeString(this)
    def companion: edu.uci.ics.amber.engine.common.actormessage.Backpressure.type = edu.uci.ics.amber.engine.common.actormessage.Backpressure
    // @@protoc_insertion_point(GeneratedMessage[edu.uci.ics.amber.engine.common.Backpressure])
}

object Backpressure extends scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.actormessage.Backpressure] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.actormessage.Backpressure] = this
  def parseFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): edu.uci.ics.amber.engine.common.actormessage.Backpressure = {
    var __enableBackpressure: _root_.scala.Boolean = false
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 8 =>
          __enableBackpressure = _input__.readBool()
        case tag => _input__.skipField(tag)
      }
    }
    edu.uci.ics.amber.engine.common.actormessage.Backpressure(
        enableBackpressure = __enableBackpressure
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[edu.uci.ics.amber.engine.common.actormessage.Backpressure] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage eq scalaDescriptor), "FieldDescriptor does not match message type.")
      edu.uci.ics.amber.engine.common.actormessage.Backpressure(
        enableBackpressure = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Boolean]).getOrElse(false)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = ActormessageProto.javaDescriptor.getMessageTypes().get(0)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = ActormessageProto.scalaDescriptor.messages(0)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = edu.uci.ics.amber.engine.common.actormessage.Backpressure(
    enableBackpressure = false
  )
  implicit class BackpressureLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.Backpressure]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.Backpressure](_l) {
    def enableBackpressure: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Boolean] = field(_.enableBackpressure)((c_, f_) => c_.copy(enableBackpressure = f_))
  }
  final val ENABLEBACKPRESSURE_FIELD_NUMBER = 1
  def of(
    enableBackpressure: _root_.scala.Boolean
  ): _root_.edu.uci.ics.amber.engine.common.actormessage.Backpressure = _root_.edu.uci.ics.amber.engine.common.actormessage.Backpressure(
    enableBackpressure
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.Backpressure])
}

@SerialVersionUID(0L)
final case class CreditUpdate(
    ) extends scalapb.GeneratedMessage with edu.uci.ics.amber.engine.common.actormessage.ActorCommand.NonEmpty with scalapb.lenses.Updatable[CreditUpdate] {
    final override def serializedSize: _root_.scala.Int = 0
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
    }
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = throw new MatchError(__fieldNumber)
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = throw new MatchError(__field)
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToSingleLineUnicodeString(this)
    def companion: edu.uci.ics.amber.engine.common.actormessage.CreditUpdate.type = edu.uci.ics.amber.engine.common.actormessage.CreditUpdate
    // @@protoc_insertion_point(GeneratedMessage[edu.uci.ics.amber.engine.common.CreditUpdate])
}

object CreditUpdate extends scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.actormessage.CreditUpdate] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.actormessage.CreditUpdate] = this
  def parseFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): edu.uci.ics.amber.engine.common.actormessage.CreditUpdate = {
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case tag => _input__.skipField(tag)
      }
    }
    edu.uci.ics.amber.engine.common.actormessage.CreditUpdate(
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[edu.uci.ics.amber.engine.common.actormessage.CreditUpdate] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage eq scalaDescriptor), "FieldDescriptor does not match message type.")
      edu.uci.ics.amber.engine.common.actormessage.CreditUpdate(
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = ActormessageProto.javaDescriptor.getMessageTypes().get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = ActormessageProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = edu.uci.ics.amber.engine.common.actormessage.CreditUpdate(
  )
  implicit class CreditUpdateLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.CreditUpdate]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, edu.uci.ics.amber.engine.common.actormessage.CreditUpdate](_l) {
  }
  def of(
  ): _root_.edu.uci.ics.amber.engine.common.actormessage.CreditUpdate = _root_.edu.uci.ics.amber.engine.common.actormessage.CreditUpdate(
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[edu.uci.ics.amber.engine.common.CreditUpdate])
}

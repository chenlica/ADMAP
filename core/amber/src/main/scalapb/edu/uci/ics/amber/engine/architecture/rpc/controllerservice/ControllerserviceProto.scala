// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package edu.uci.ics.amber.engine.architecture.rpc.controllerservice

object ControllerserviceProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ControlcommandsProto,
    edu.uci.ics.amber.engine.architecture.rpc.controlreturns.ControlreturnsProto,
    scalapb.options.ScalapbProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  private lazy val ProtoBytes: _root_.scala.Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """CkFlZHUvdWNpL2ljcy9hbWJlci9lbmdpbmUvYXJjaGl0ZWN0dXJlL3JwYy9jb250cm9sbGVyc2VydmljZS5wcm90bxIpZWR1L
  nVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMaP2VkdS91Y2kvaWNzL2FtYmVyL2VuZ2luZS9hcmNoaXRlY3R1c
  mUvcnBjL2NvbnRyb2xjb21tYW5kcy5wcm90bxo+ZWR1L3VjaS9pY3MvYW1iZXIvZW5naW5lL2FyY2hpdGVjdHVyZS9ycGMvY29ud
  HJvbHJldHVybnMucHJvdG8aFXNjYWxhcGIvc2NhbGFwYi5wcm90bzLdEQoRQ29udHJvbGxlclNlcnZpY2USmgEKFVJldHJpZXZlV
  29ya2Zsb3dTdGF0ZRI3LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLkVtcHR5UmVxdWVzdBpILmVkd
  S51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlJldHJpZXZlV29ya2Zsb3dTdGF0ZVJlc3BvbnNlEq0BChZQc
  m9wYWdhdGVDaGFubmVsTWFya2VyEkguZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuUHJvcGFnYXRlQ
  2hhbm5lbE1hcmtlclJlcXVlc3QaSS5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5Qcm9wYWdhdGVDa
  GFubmVsTWFya2VyUmVzcG9uc2USpwEKFFRha2VHbG9iYWxDaGVja3BvaW50EkYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY
  2hpdGVjdHVyZS5ycGMuVGFrZUdsb2JhbENoZWNrcG9pbnRSZXF1ZXN0GkcuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpd
  GVjdHVyZS5ycGMuVGFrZUdsb2JhbENoZWNrcG9pbnRSZXNwb25zZRKGAQoMRGVidWdDb21tYW5kEj4uZWR1LnVjaS5pY3MuYW1iZ
  XIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRGVidWdDb21tYW5kUmVxdWVzdBo2LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hc
  mNoaXRlY3R1cmUucnBjLkVtcHR5UmV0dXJuErMBChhFdmFsdWF0ZVB5dGhvbkV4cHJlc3Npb24SSi5lZHUudWNpLmljcy5hbWJlc
  i5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5FdmFsdWF0ZVB5dGhvbkV4cHJlc3Npb25SZXF1ZXN0GksuZWR1LnVjaS5pY3MuYW1iZ
  XIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRXZhbHVhdGVQeXRob25FeHByZXNzaW9uUmVzcG9uc2USnAEKF0NvbnNvbGVNZXNzY
  WdlVHJpZ2dlcmVkEkkuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuQ29uc29sZU1lc3NhZ2VUcmlnZ
  2VyZWRSZXF1ZXN0GjYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRW1wdHlSZXR1cm4SiAEKDVBvc
  nRDb21wbGV0ZWQSPy5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5Qb3J0Q29tcGxldGVkUmVxdWVzd
  Bo2LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLkVtcHR5UmV0dXJuEooBCg1TdGFydFdvcmtmbG93E
  jcuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRW1wdHlSZXF1ZXN0GkAuZWR1LnVjaS5pY3MuYW1iZ
  XIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuU3RhcnRXb3JrZmxvd1Jlc3BvbnNlEoEBCg5SZXN1bWVXb3JrZmxvdxI3LmVkdS51Y
  2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLkVtcHR5UmVxdWVzdBo2LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZ
  S5hcmNoaXRlY3R1cmUucnBjLkVtcHR5UmV0dXJuEoABCg1QYXVzZVdvcmtmbG93EjcuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lL
  mFyY2hpdGVjdHVyZS5ycGMuRW1wdHlSZXF1ZXN0GjYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuR
  W1wdHlSZXR1cm4SkgEKEldvcmtlclN0YXRlVXBkYXRlZBJELmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUuc
  nBjLldvcmtlclN0YXRlVXBkYXRlZFJlcXVlc3QaNi5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5Fb
  XB0eVJldHVybhKLAQoYV29ya2VyRXhlY3V0aW9uQ29tcGxldGVkEjcuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjd
  HVyZS5ycGMuRW1wdHlSZXF1ZXN0GjYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRW1wdHlSZXR1c
  m4ShAEKC0xpbmtXb3JrZXJzEj0uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuTGlua1dvcmtlcnNSZ
  XF1ZXN0GjYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRW1wdHlSZXR1cm4SngEKIUNvbnRyb2xsZ
  XJJbml0aWF0ZVF1ZXJ5U3RhdGlzdGljcxJBLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlF1ZXJ5U
  3RhdGlzdGljc1JlcXVlc3QaNi5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5FbXB0eVJldHVybhKIA
  QoNUmV0cnlXb3JrZmxvdxI/LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlJldHJ5V29ya2Zsb3dSZ
  XF1ZXN0GjYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRW1wdHlSZXR1cm5CCeI/BkgAWAB4AWIGc
  HJvdG8z"""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor = {
    val javaProto = com.google.protobuf.DescriptorProtos.FileDescriptorProto.parseFrom(ProtoBytes)
    com.google.protobuf.Descriptors.FileDescriptor.buildFrom(javaProto, _root_.scala.Array(
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ControlcommandsProto.javaDescriptor,
      edu.uci.ics.amber.engine.architecture.rpc.controlreturns.ControlreturnsProto.javaDescriptor,
      scalapb.options.ScalapbProto.javaDescriptor
    ))
  }
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}
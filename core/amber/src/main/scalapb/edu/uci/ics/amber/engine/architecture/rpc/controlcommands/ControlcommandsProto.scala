// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package edu.uci.ics.amber.engine.architecture.rpc.controlcommands

object ControlcommandsProto extends _root_.scalapb.GeneratedFileObject {
  lazy val dependencies: Seq[_root_.scalapb.GeneratedFileObject] = Seq(
    edu.uci.ics.amber.engine.common.virtualidentity.VirtualidentityProto,
    edu.uci.ics.amber.engine.common.workflow.WorkflowProto,
    edu.uci.ics.amber.engine.architecture.worker.statistics.StatisticsProto,
    edu.uci.ics.amber.engine.architecture.sendsemantics.partitionings.PartitioningsProto,
    scalapb.options.ScalapbProto,
    com.google.protobuf.timestamp.TimestampProto,
    com.google.protobuf.any.AnyProto
  )
  lazy val messagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] =
    Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]](
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ControlRequestMessage,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.EmptyRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.AsyncRPCContext,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ControlInvocation,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ChannelMarkerPayload,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.PropagateChannelMarkerRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.TakeGlobalCheckpointRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.WorkflowReconfigureRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.DebugCommandRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.EvaluatePythonExpressionRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ModifyLogicRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.RetryWorkflowRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ConsoleMessage,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ConsoleMessageTriggeredRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.PortCompletedRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.WorkerStateUpdatedRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.LinkWorkersRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.Ping,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.Pong,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.Pass,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.Nested,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.MultiCall,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.ErrorCommand,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.Collect,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.GenerateNumber,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.Chain,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.Recursion,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.AddInputChannelRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.AddPartitioningRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.AssignPortRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.FinalizeCheckpointRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.InitializeExecutorRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.UpdateExecutorRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.PrepareCheckpointRequest,
      edu.uci.ics.amber.engine.architecture.rpc.controlcommands.QueryStatisticsRequest
    )
  private lazy val ProtoBytes: _root_.scala.Array[Byte] =
      scalapb.Encoding.fromBase64(scala.collection.immutable.Seq(
  """Cj9lZHUvdWNpL2ljcy9hbWJlci9lbmdpbmUvYXJjaGl0ZWN0dXJlL3JwYy9jb250cm9sY29tbWFuZHMucHJvdG8SKWVkdS51Y
  2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjGjVlZHUvdWNpL2ljcy9hbWJlci9lbmdpbmUvY29tbW9uL3ZpcnR1Y
  WxpZGVudGl0eS5wcm90bxouZWR1L3VjaS9pY3MvYW1iZXIvZW5naW5lL2NvbW1vbi93b3JrZmxvdy5wcm90bxo9ZWR1L3VjaS9pY
  3MvYW1iZXIvZW5naW5lL2FyY2hpdGVjdHVyZS93b3JrZXIvc3RhdGlzdGljcy5wcm90bxpHZWR1L3VjaS9pY3MvYW1iZXIvZW5na
  W5lL2FyY2hpdGVjdHVyZS9zZW5kc2VtYW50aWNzL3BhcnRpdGlvbmluZ3MucHJvdG8aFXNjYWxhcGIvc2NhbGFwYi5wcm90bxofZ
  29vZ2xlL3Byb3RvYnVmL3RpbWVzdGFtcC5wcm90bxoZZ29vZ2xlL3Byb3RvYnVmL2FueS5wcm90byKAHwoOQ29udHJvbFJlcXVlc
  3QStAEKHXByb3BhZ2F0ZUNoYW5uZWxNYXJrZXJSZXF1ZXN0GAEgASgLMkguZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpd
  GVjdHVyZS5ycGMuUHJvcGFnYXRlQ2hhbm5lbE1hcmtlclJlcXVlc3RCIuI/HxIdcHJvcGFnYXRlQ2hhbm5lbE1hcmtlclJlcXVlc
  3RIAFIdcHJvcGFnYXRlQ2hhbm5lbE1hcmtlclJlcXVlc3QSrAEKG3Rha2VHbG9iYWxDaGVja3BvaW50UmVxdWVzdBgCIAEoCzJGL
  mVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlRha2VHbG9iYWxDaGVja3BvaW50UmVxdWVzdEIg4j8dE
  ht0YWtlR2xvYmFsQ2hlY2twb2ludFJlcXVlc3RIAFIbdGFrZUdsb2JhbENoZWNrcG9pbnRSZXF1ZXN0EowBChNkZWJ1Z0NvbW1hb
  mRSZXF1ZXN0GAMgASgLMj4uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuRGVidWdDb21tYW5kUmVxd
  WVzdEIY4j8VEhNkZWJ1Z0NvbW1hbmRSZXF1ZXN0SABSE2RlYnVnQ29tbWFuZFJlcXVlc3QSvAEKH2V2YWx1YXRlUHl0aG9uRXhwc
  mVzc2lvblJlcXVlc3QYBCABKAsySi5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5FdmFsdWF0ZVB5d
  GhvbkV4cHJlc3Npb25SZXF1ZXN0QiTiPyESH2V2YWx1YXRlUHl0aG9uRXhwcmVzc2lvblJlcXVlc3RIAFIfZXZhbHVhdGVQeXRob
  25FeHByZXNzaW9uUmVxdWVzdBKIAQoSbW9kaWZ5TG9naWNSZXF1ZXN0GAUgASgLMj0uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lL
  mFyY2hpdGVjdHVyZS5ycGMuTW9kaWZ5TG9naWNSZXF1ZXN0QhfiPxQSEm1vZGlmeUxvZ2ljUmVxdWVzdEgAUhJtb2RpZnlMb2dpY
  1JlcXVlc3QSkAEKFHJldHJ5V29ya2Zsb3dSZXF1ZXN0GAYgASgLMj8uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjd
  HVyZS5ycGMuUmV0cnlXb3JrZmxvd1JlcXVlc3RCGeI/FhIUcmV0cnlXb3JrZmxvd1JlcXVlc3RIAFIUcmV0cnlXb3JrZmxvd1Jlc
  XVlc3QSuAEKHmNvbnNvbGVNZXNzYWdlVHJpZ2dlcmVkUmVxdWVzdBgIIAEoCzJJLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hc
  mNoaXRlY3R1cmUucnBjLkNvbnNvbGVNZXNzYWdlVHJpZ2dlcmVkUmVxdWVzdEIj4j8gEh5jb25zb2xlTWVzc2FnZVRyaWdnZXJlZ
  FJlcXVlc3RIAFIeY29uc29sZU1lc3NhZ2VUcmlnZ2VyZWRSZXF1ZXN0EpABChRwb3J0Q29tcGxldGVkUmVxdWVzdBgJIAEoCzI/L
  mVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlBvcnRDb21wbGV0ZWRSZXF1ZXN0QhniPxYSFHBvcnRDb
  21wbGV0ZWRSZXF1ZXN0SABSFHBvcnRDb21wbGV0ZWRSZXF1ZXN0EqQBChl3b3JrZXJTdGF0ZVVwZGF0ZWRSZXF1ZXN0GAogASgLM
  kQuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuV29ya2VyU3RhdGVVcGRhdGVkUmVxdWVzdEIe4j8bE
  hl3b3JrZXJTdGF0ZVVwZGF0ZWRSZXF1ZXN0SABSGXdvcmtlclN0YXRlVXBkYXRlZFJlcXVlc3QSiAEKEmxpbmtXb3JrZXJzUmVxd
  WVzdBgLIAEoCzI9LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLkxpbmtXb3JrZXJzUmVxdWVzdEIX4
  j8UEhJsaW5rV29ya2Vyc1JlcXVlc3RIAFISbGlua1dvcmtlcnNSZXF1ZXN0EpgBChZhZGRJbnB1dENoYW5uZWxSZXF1ZXN0GDIgA
  SgLMkEuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuQWRkSW5wdXRDaGFubmVsUmVxdWVzdEIb4j8YE
  hZhZGRJbnB1dENoYW5uZWxSZXF1ZXN0SABSFmFkZElucHV0Q2hhbm5lbFJlcXVlc3QSmAEKFmFkZFBhcnRpdGlvbmluZ1JlcXVlc
  3QYMyABKAsyQS5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5BZGRQYXJ0aXRpb25pbmdSZXF1ZXN0Q
  hviPxgSFmFkZFBhcnRpdGlvbmluZ1JlcXVlc3RIAFIWYWRkUGFydGl0aW9uaW5nUmVxdWVzdBKEAQoRYXNzaWduUG9ydFJlcXVlc
  3QYNCABKAsyPC5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5Bc3NpZ25Qb3J0UmVxdWVzdEIW4j8TE
  hFhc3NpZ25Qb3J0UmVxdWVzdEgAUhFhc3NpZ25Qb3J0UmVxdWVzdBKkAQoZZmluYWxpemVDaGVja3BvaW50UmVxdWVzdBg1IAEoC
  zJELmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLkZpbmFsaXplQ2hlY2twb2ludFJlcXVlc3RCHuI/G
  xIZZmluYWxpemVDaGVja3BvaW50UmVxdWVzdEgAUhlmaW5hbGl6ZUNoZWNrcG9pbnRSZXF1ZXN0EqQBChlpbml0aWFsaXplRXhlY
  3V0b3JSZXF1ZXN0GDYgASgLMkQuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuSW5pdGlhbGl6ZUV4Z
  WN1dG9yUmVxdWVzdEIe4j8bEhlpbml0aWFsaXplRXhlY3V0b3JSZXF1ZXN0SABSGWluaXRpYWxpemVFeGVjdXRvclJlcXVlc3QSl
  AEKFXVwZGF0ZUV4ZWN1dG9yUmVxdWVzdBg3IAEoCzJALmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjL
  lVwZGF0ZUV4ZWN1dG9yUmVxdWVzdEIa4j8XEhV1cGRhdGVFeGVjdXRvclJlcXVlc3RIAFIVdXBkYXRlRXhlY3V0b3JSZXF1ZXN0E
  nAKDGVtcHR5UmVxdWVzdBg4IAEoCzI3LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLkVtcHR5UmVxd
  WVzdEIR4j8OEgxlbXB0eVJlcXVlc3RIAFIMZW1wdHlSZXF1ZXN0EqABChhwcmVwYXJlQ2hlY2twb2ludFJlcXVlc3QYOSABKAsyQ
  y5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5QcmVwYXJlQ2hlY2twb2ludFJlcXVlc3RCHeI/GhIYc
  HJlcGFyZUNoZWNrcG9pbnRSZXF1ZXN0SABSGHByZXBhcmVDaGVja3BvaW50UmVxdWVzdBKYAQoWcXVlcnlTdGF0aXN0aWNzUmVxd
  WVzdBg6IAEoCzJBLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlF1ZXJ5U3RhdGlzdGljc1JlcXVlc
  3RCG+I/GBIWcXVlcnlTdGF0aXN0aWNzUmVxdWVzdEgAUhZxdWVyeVN0YXRpc3RpY3NSZXF1ZXN0ElAKBHBpbmcYZCABKAsyLy5lZ
  HUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5QaW5nQgniPwYSBHBpbmdIAFIEcGluZxJQCgRwb25nGGUgA
  SgLMi8uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuUG9uZ0IJ4j8GEgRwb25nSABSBHBvbmcSWAoGb
  mVzdGVkGGYgASgLMjEuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuTmVzdGVkQgviPwgSBm5lc3RlZ
  EgAUgZuZXN0ZWQSUAoEcGFzcxhnIAEoCzIvLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlBhc3NCC
  eI/BhIEcGFzc0gAUgRwYXNzEnAKDGVycm9yQ29tbWFuZBhoIAEoCzI3LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY
  3R1cmUucnBjLkVycm9yQ29tbWFuZEIR4j8OEgxlcnJvckNvbW1hbmRIAFIMZXJyb3JDb21tYW5kEmQKCXJlY3Vyc2lvbhhpIAEoC
  zI0LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLlJlY3Vyc2lvbkIO4j8LEglyZWN1cnNpb25IAFIJc
  mVjdXJzaW9uElwKB2NvbGxlY3QYaiABKAsyMi5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5Db2xsZ
  WN0QgziPwkSB2NvbGxlY3RIAFIHY29sbGVjdBJ4Cg5nZW5lcmF0ZU51bWJlchhrIAEoCzI5LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ
  2luZS5hcmNoaXRlY3R1cmUucnBjLkdlbmVyYXRlTnVtYmVyQhPiPxASDmdlbmVyYXRlTnVtYmVySABSDmdlbmVyYXRlTnVtYmVyE
  mQKCW11bHRpQ2FsbBhsIAEoCzI0LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLk11bHRpQ2FsbEIO4
  j8LEgltdWx0aUNhbGxIAFIJbXVsdGlDYWxsElQKBWNoYWluGG0gASgLMjAuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpd
  GVjdHVyZS5ycGMuQ2hhaW5CCuI/BxIFY2hhaW5IAFIFY2hhaW5CDgoMc2VhbGVkX3ZhbHVlIg4KDEVtcHR5UmVxdWVzdCLcAQoPQ
  XN5bmNSUENDb250ZXh0El0KBnNlbmRlchgBIAEoCzI1LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uQWN0b3JWaXJ0d
  WFsSWRlbnRpdHlCDuI/CxIGc2VuZGVy8AEBUgZzZW5kZXISYwoIcmVjZWl2ZXIYAiABKAsyNS5lZHUudWNpLmljcy5hbWJlci5lb
  mdpbmUuY29tbW9uLkFjdG9yVmlydHVhbElkZW50aXR5QhDiPw0SCHJlY2VpdmVy8AEBUghyZWNlaXZlcjoF4j8COAEi/gIKEUNvb
  nRyb2xJbnZvY2F0aW9uEi8KCm1ldGhvZE5hbWUYASABKAlCD+I/DBIKbWV0aG9kTmFtZVIKbWV0aG9kTmFtZRJkCgdjb21tYW5kG
  AIgASgLMjkuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuQ29udHJvbFJlcXVlc3RCD+I/DBIHY29tb
  WFuZPABAVIHY29tbWFuZBJiCgdjb250ZXh0GAMgASgLMjouZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5yc
  GMuQXN5bmNSUENDb250ZXh0QgziPwkSB2NvbnRleHRSB2NvbnRleHQSLAoJY29tbWFuZElkGAQgASgDQg7iPwsSCWNvbW1hbmRJZ
  FIJY29tbWFuZElkOkDiPz0KO2VkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uYW1iZXJtZXNzYWdlLkNvbnRyb2xQYXlsb
  2FkIqYFChRDaGFubmVsTWFya2VyUGF5bG9hZBJSCgJpZBgBIAEoCzI2LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uQ
  2hhbm5lbE1hcmtlcklkZW50aXR5QgriPwcSAmlk8AEBUgJpZBJtCgptYXJrZXJUeXBlGAIgASgOMjwuZWR1LnVjaS5pY3MuYW1iZ
  XIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuQ2hhbm5lbE1hcmtlclR5cGVCD+I/DBIKbWFya2VyVHlwZVIKbWFya2VyVHlwZRJSC
  gVzY29wZRgDIAMoCzIwLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uQ2hhbm5lbElkZW50aXR5QgriPwcSBXNjb3BlU
  gVzY29wZRKQAQoOY29tbWFuZE1hcHBpbmcYBCADKAsyUy5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwY
  y5DaGFubmVsTWFya2VyUGF5bG9hZC5Db21tYW5kTWFwcGluZ0VudHJ5QhPiPxASDmNvbW1hbmRNYXBwaW5nUg5jb21tYW5kTWFwc
  GluZxqVAQoTQ29tbWFuZE1hcHBpbmdFbnRyeRIaCgNrZXkYASABKAlCCOI/BRIDa2V5UgNrZXkSXgoFdmFsdWUYAiABKAsyPC5lZ
  HUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5Db250cm9sSW52b2NhdGlvbkIK4j8HEgV2YWx1ZVIFdmFsd
  WU6AjgBOkziP0kKR2VkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uYW1iZXJtZXNzYWdlLldvcmtmbG93RklGT01lc3NhZ
  2VQYXlsb2FkItUFCh1Qcm9wYWdhdGVDaGFubmVsTWFya2VyUmVxdWVzdBJ/ChNzb3VyY2VPcFRvU3RhcnRQcm9wGAEgAygLMjMuZ
  WR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5QaHlzaWNhbE9wSWRlbnRpdHlCGOI/FRITc291cmNlT3BUb1N0YXJ0UHJvc
  FITc291cmNlT3BUb1N0YXJ0UHJvcBJSCgJpZBgCIAEoCzI2LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uQ2hhbm5lb
  E1hcmtlcklkZW50aXR5QgriPwcSAmlk8AEBUgJpZBJtCgptYXJrZXJUeXBlGAMgASgOMjwuZWR1LnVjaS5pY3MuYW1iZXIuZW5na
  W5lLmFyY2hpdGVjdHVyZS5ycGMuQ2hhbm5lbE1hcmtlclR5cGVCD+I/DBIKbWFya2VyVHlwZVIKbWFya2VyVHlwZRJVCgVzY29wZ
  RgEIAMoCzIzLmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5jb21tb24uUGh5c2ljYWxPcElkZW50aXR5QgriPwcSBXNjb3BlUgVzY
  29wZRJhCgl0YXJnZXRPcHMYBSADKAsyMy5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuY29tbW9uLlBoeXNpY2FsT3BJZGVudGl0e
  UIO4j8LEgl0YXJnZXRPcHNSCXRhcmdldE9wcxJzCg1tYXJrZXJDb21tYW5kGAYgASgLMjkuZWR1LnVjaS5pY3MuYW1iZXIuZW5na
  W5lLmFyY2hpdGVjdHVyZS5ycGMuQ29udHJvbFJlcXVlc3RCEuI/DxINbWFya2VyQ29tbWFuZFINbWFya2VyQ29tbWFuZBJBChBtY
  XJrZXJNZXRob2ROYW1lGAcgASgJQhXiPxISEG1hcmtlck1ldGhvZE5hbWVSEG1hcmtlck1ldGhvZE5hbWUigAIKG1Rha2VHbG9iY
  WxDaGVja3BvaW50UmVxdWVzdBI7Cg5lc3RpbWF0aW9uT25seRgBIAEoCEIT4j8QEg5lc3RpbWF0aW9uT25seVIOZXN0aW1hdGlvb
  k9ubHkScAoMY2hlY2twb2ludElkGAIgASgLMjYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5DaGFubmVsTWFya2VyS
  WRlbnRpdHlCFOI/ERIMY2hlY2twb2ludElk8AEBUgxjaGVja3BvaW50SWQSMgoLZGVzdGluYXRpb24YAyABKAlCEOI/DRILZGVzd
  GluYXRpb25SC2Rlc3RpbmF0aW9uIuUBChpXb3JrZmxvd1JlY29uZmlndXJlUmVxdWVzdBKAAQoPcmVjb25maWd1cmF0aW9uGAEgA
  SgLMj0uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuTW9kaWZ5TG9naWNSZXF1ZXN0QhfiPxQSD3JlY
  29uZmlndXJhdGlvbvABAVIPcmVjb25maWd1cmF0aW9uEkQKEXJlY29uZmlndXJhdGlvbklkGAIgASgJQhbiPxMSEXJlY29uZmlnd
  XJhdGlvbklkUhFyZWNvbmZpZ3VyYXRpb25JZCJcChNEZWJ1Z0NvbW1hbmRSZXF1ZXN0EikKCHdvcmtlcklkGAEgASgJQg3iPwoSC
  HdvcmtlcklkUgh3b3JrZXJJZBIaCgNjbWQYAiABKAlCCOI/BRIDY21kUgNjbWQigwEKH0V2YWx1YXRlUHl0aG9uRXhwcmVzc2lvb
  lJlcXVlc3QSLwoKZXhwcmVzc2lvbhgBIAEoCUIP4j8MEgpleHByZXNzaW9uUgpleHByZXNzaW9uEi8KCm9wZXJhdG9ySWQYAiABK
  AlCD+I/DBIKb3BlcmF0b3JJZFIKb3BlcmF0b3JJZCKQAQoSTW9kaWZ5TG9naWNSZXF1ZXN0EnoKDXVwZGF0ZVJlcXVlc3QYASADK
  AsyQC5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5VcGRhdGVFeGVjdXRvclJlcXVlc3RCEuI/DxINd
  XBkYXRlUmVxdWVzdFINdXBkYXRlUmVxdWVzdCJ1ChRSZXRyeVdvcmtmbG93UmVxdWVzdBJdCgd3b3JrZXJzGAEgAygLMjUuZWR1L
  nVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5BY3RvclZpcnR1YWxJZGVudGl0eUIM4j8JEgd3b3JrZXJzUgd3b3JrZXJzIqMDC
  g5Db25zb2xlTWVzc2FnZRIqCgl3b3JrZXJfaWQYASABKAlCDeI/ChIId29ya2VySWRSCHdvcmtlcklkEksKCXRpbWVzdGFtcBgCI
  AEoCzIaLmdvb2dsZS5wcm90b2J1Zi5UaW1lc3RhbXBCEeI/DhIJdGltZXN0YW1w8AEBUgl0aW1lc3RhbXASZgoIbXNnX3R5cGUYA
  yABKA4yPS5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnJwYy5Db25zb2xlTWVzc2FnZVR5cGVCDOI/CRIHb
  XNnVHlwZVIHbXNnVHlwZRIjCgZzb3VyY2UYBCABKAlCC+I/CBIGc291cmNlUgZzb3VyY2USIAoFdGl0bGUYBSABKAlCCuI/BxIFd
  Gl0bGVSBXRpdGxlEiYKB21lc3NhZ2UYBiABKAlCDOI/CRIHbWVzc2FnZVIHbWVzc2FnZTpB4j8+CjxlZHUudWNpLmljcy5hbWJlc
  i5lbmdpbmUuYXJjaGl0ZWN0dXJlLmNvbnRyb2xsZXIuQ2xpZW50RXZlbnQimwEKHkNvbnNvbGVNZXNzYWdlVHJpZ2dlcmVkUmVxd
  WVzdBJ5Cg5jb25zb2xlTWVzc2FnZRgBIAEoCzI5LmVkdS51Y2kuaWNzLmFtYmVyLmVuZ2luZS5hcmNoaXRlY3R1cmUucnBjLkNvb
  nNvbGVNZXNzYWdlQhbiPxMSDmNvbnNvbGVNZXNzYWdl8AEBUg5jb25zb2xlTWVzc2FnZSKPAQoUUG9ydENvbXBsZXRlZFJlcXVlc
  3QSVQoGcG9ydElkGAEgASgLMi0uZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5Qb3J0SWRlbnRpdHlCDuI/CxIGcG9yd
  Elk8AEBUgZwb3J0SWQSIAoFaW5wdXQYAiABKAhCCuI/BxIFaW5wdXRSBWlucHV0InsKGVdvcmtlclN0YXRlVXBkYXRlZFJlcXVlc
  3QSXgoFc3RhdGUYASABKA4yOS5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLndvcmtlci5Xb3JrZXJTdGF0Z
  UIN4j8KEgVzdGF0ZfABAVIFc3RhdGUiZQoSTGlua1dvcmtlcnNSZXF1ZXN0Ek8KBGxpbmsYASABKAsyLS5lZHUudWNpLmljcy5hb
  WJlci5lbmdpbmUuY29tbW9uLlBoeXNpY2FsTGlua0IM4j8JEgRsaW5r8AEBUgRsaW5rIosBCgRQaW5nEhQKAWkYASABKAVCBuI/A
  xIBaVIBaRIaCgNlbmQYAiABKAVCCOI/BRIDZW5kUgNlbmQSUQoCdG8YAyABKAsyNS5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuY
  29tbW9uLkFjdG9yVmlydHVhbElkZW50aXR5QgriPwcSAnRv8AEBUgJ0byKLAQoEUG9uZxIUCgFpGAEgASgFQgbiPwMSAWlSAWkSG
  goDZW5kGAIgASgFQgjiPwUSA2VuZFIDZW5kElEKAnRvGAMgASgLMjUuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5BY
  3RvclZpcnR1YWxJZGVudGl0eUIK4j8HEgJ0b/ABAVICdG8iKAoEUGFzcxIgCgV2YWx1ZRgBIAEoCUIK4j8HEgV2YWx1ZVIFdmFsd
  WUiHgoGTmVzdGVkEhQKAWsYASABKAVCBuI/AxIBa1IBayJeCglNdWx0aUNhbGwSUQoDc2VxGAEgAygLMjUuZWR1LnVjaS5pY3MuY
  W1iZXIuZW5naW5lLmNvbW1vbi5BY3RvclZpcnR1YWxJZGVudGl0eUII4j8FEgNzZXFSA3NlcSIOCgxFcnJvckNvbW1hbmQiaAoHQ
  29sbGVjdBJdCgd3b3JrZXJzGAEgAygLMjUuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5BY3RvclZpcnR1YWxJZGVud
  Gl0eUIM4j8JEgd3b3JrZXJzUgd3b3JrZXJzIhAKDkdlbmVyYXRlTnVtYmVyImAKBUNoYWluElcKBW5leHRzGAEgAygLMjUuZWR1L
  nVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5BY3RvclZpcnR1YWxJZGVudGl0eUIK4j8HEgVuZXh0c1IFbmV4dHMiIQoJUmVjd
  XJzaW9uEhQKAWkYASABKAVCBuI/AxIBaVIBaSLSAQoWQWRkSW5wdXRDaGFubmVsUmVxdWVzdBJhCgljaGFubmVsSWQYASABKAsyM
  C5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuY29tbW9uLkNoYW5uZWxJZGVudGl0eUIR4j8OEgljaGFubmVsSWTwAQFSCWNoYW5uZ
  WxJZBJVCgZwb3J0SWQYAiABKAsyLS5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuY29tbW9uLlBvcnRJZGVudGl0eUIO4j8LEgZwb
  3J0SWTwAQFSBnBvcnRJZCLjAQoWQWRkUGFydGl0aW9uaW5nUmVxdWVzdBJMCgN0YWcYASABKAsyLS5lZHUudWNpLmljcy5hbWJlc
  i5lbmdpbmUuY29tbW9uLlBoeXNpY2FsTGlua0IL4j8IEgN0YWfwAQFSA3RhZxJ7CgxwYXJ0aXRpb25pbmcYAiABKAsyQS5lZHUud
  WNpLmljcy5hbWJlci5lbmdpbmUuYXJjaGl0ZWN0dXJlLnNlbmRzZW1hbnRpY3MuUGFydGl0aW9uaW5nQhTiPxESDHBhcnRpdGlvb
  mluZ/ABAVIMcGFydGl0aW9uaW5nIswCChFBc3NpZ25Qb3J0UmVxdWVzdBJVCgZwb3J0SWQYASABKAsyLS5lZHUudWNpLmljcy5hb
  WJlci5lbmdpbmUuY29tbW9uLlBvcnRJZGVudGl0eUIO4j8LEgZwb3J0SWTwAQFSBnBvcnRJZBIgCgVpbnB1dBgCIAEoCEIK4j8HE
  gVpbnB1dFIFaW5wdXQSbQoGc2NoZW1hGAMgAygLMkguZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmFyY2hpdGVjdHVyZS5ycGMuQ
  XNzaWduUG9ydFJlcXVlc3QuU2NoZW1hRW50cnlCC+I/CBIGc2NoZW1hUgZzY2hlbWEaTwoLU2NoZW1hRW50cnkSGgoDa2V5GAEgA
  SgJQgjiPwUSA2tleVIDa2V5EiAKBXZhbHVlGAIgASgJQgriPwcSBXZhbHVlUgV2YWx1ZToCOAEitQEKGUZpbmFsaXplQ2hlY2twb
  2ludFJlcXVlc3QScAoMY2hlY2twb2ludElkGAEgASgLMjYuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5DaGFubmVsT
  WFya2VySWRlbnRpdHlCFOI/ERIMY2hlY2twb2ludElk8AEBUgxjaGVja3BvaW50SWQSJgoHd3JpdGVUbxgCIAEoCUIM4j8JEgd3c
  ml0ZVRvUgd3cml0ZVRvIooCChlJbml0aWFsaXplRXhlY3V0b3JSZXF1ZXN0EkEKEHRvdGFsV29ya2VyQ291bnQYASABKAVCFeI/E
  hIQdG90YWxXb3JrZXJDb3VudFIQdG90YWxXb3JrZXJDb3VudBJUCg5vcEV4ZWNJbml0SW5mbxgCIAEoCzIULmdvb2dsZS5wcm90b
  2J1Zi5BbnlCFuI/ExIOb3BFeGVjSW5pdEluZm/wAQFSDm9wRXhlY0luaXRJbmZvEikKCGlzU291cmNlGAMgASgIQg3iPwoSCGlzU
  291cmNlUghpc1NvdXJjZRIpCghsYW5ndWFnZRgEIAEoCUIN4j8KEghsYW5ndWFnZVIIbGFuZ3VhZ2UiqQIKFVVwZGF0ZUV4ZWN1d
  G9yUmVxdWVzdBJnCgp0YXJnZXRPcElkGAEgASgLMjMuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5QaHlzaWNhbE9wS
  WRlbnRpdHlCEuI/DxIKdGFyZ2V0T3BJZPABAVIKdGFyZ2V0T3BJZBJLCgtuZXdFeGVjdXRvchgCIAEoCzIULmdvb2dsZS5wcm90b
  2J1Zi5BbnlCE+I/EBILbmV3RXhlY3V0b3LwAQFSC25ld0V4ZWN1dG9yEloKEXN0YXRlVHJhbnNmZXJGdW5jGAMgASgLMhQuZ29vZ
  2xlLnByb3RvYnVmLkFueUIW4j8TEhFzdGF0ZVRyYW5zZmVyRnVuY1IRc3RhdGVUcmFuc2ZlckZ1bmMiyQEKGFByZXBhcmVDaGVja
  3BvaW50UmVxdWVzdBJwCgxjaGVja3BvaW50SWQYASABKAsyNi5lZHUudWNpLmljcy5hbWJlci5lbmdpbmUuY29tbW9uLkNoYW5uZ
  WxNYXJrZXJJZGVudGl0eUIU4j8REgxjaGVja3BvaW50SWTwAQFSDGNoZWNrcG9pbnRJZBI7Cg5lc3RpbWF0aW9uT25seRgCIAEoC
  EIT4j8QEg5lc3RpbWF0aW9uT25seVIOZXN0aW1hdGlvbk9ubHkijwEKFlF1ZXJ5U3RhdGlzdGljc1JlcXVlc3QSdQoPZmlsdGVyQ
  nlXb3JrZXJzGAEgAygLMjUuZWR1LnVjaS5pY3MuYW1iZXIuZW5naW5lLmNvbW1vbi5BY3RvclZpcnR1YWxJZGVudGl0eUIU4j8RE
  g9maWx0ZXJCeVdvcmtlcnNSD2ZpbHRlckJ5V29ya2VycypnChFDaGFubmVsTWFya2VyVHlwZRItChFSRVFVSVJFX0FMSUdOTUVOV
  BAAGhbiPxMSEVJFUVVJUkVfQUxJR05NRU5UEiMKDE5PX0FMSUdOTUVOVBABGhHiPw4SDE5PX0FMSUdOTUVOVCp6ChJDb25zb2xlT
  WVzc2FnZVR5cGUSFQoFUFJJTlQQABoK4j8HEgVQUklOVBIVCgVFUlJPUhABGgriPwcSBUVSUk9SEhkKB0NPTU1BTkQQAhoM4j8JE
  gdDT01NQU5EEhsKCERFQlVHR0VSEAMaDeI/ChIIREVCVUdHRVJCCeI/BkgAWAB4AWIGcHJvdG8z"""
      ).mkString)
  lazy val scalaDescriptor: _root_.scalapb.descriptors.FileDescriptor = {
    val scalaProto = com.google.protobuf.descriptor.FileDescriptorProto.parseFrom(ProtoBytes)
    _root_.scalapb.descriptors.FileDescriptor.buildFrom(scalaProto, dependencies.map(_.scalaDescriptor))
  }
  lazy val javaDescriptor: com.google.protobuf.Descriptors.FileDescriptor = {
    val javaProto = com.google.protobuf.DescriptorProtos.FileDescriptorProto.parseFrom(ProtoBytes)
    com.google.protobuf.Descriptors.FileDescriptor.buildFrom(javaProto, _root_.scala.Array(
      edu.uci.ics.amber.engine.common.virtualidentity.VirtualidentityProto.javaDescriptor,
      edu.uci.ics.amber.engine.common.workflow.WorkflowProto.javaDescriptor,
      edu.uci.ics.amber.engine.architecture.worker.statistics.StatisticsProto.javaDescriptor,
      edu.uci.ics.amber.engine.architecture.sendsemantics.partitionings.PartitioningsProto.javaDescriptor,
      scalapb.options.ScalapbProto.javaDescriptor,
      com.google.protobuf.timestamp.TimestampProto.javaDescriptor,
      com.google.protobuf.any.AnyProto.javaDescriptor
    ))
  }
  @deprecated("Use javaDescriptor instead. In a future version this will refer to scalaDescriptor.", "ScalaPB 0.5.47")
  def descriptor: com.google.protobuf.Descriptors.FileDescriptor = javaDescriptor
}
package edu.uci.ics.amber.engine.architecture.worker.managers

import edu.uci.ics.amber.core.executor.OpExecInitInfo.generateJavaOpExec
import edu.uci.ics.amber.core.executor.{OpExecInitInfo, OperatorExecutor}
import edu.uci.ics.amber.core.tuple.TupleLike
import edu.uci.ics.amber.engine.architecture.rpc.controlcommands.InitializeExecutorRequest
import edu.uci.ics.amber.engine.common.{
  AmberLogging,
  AmberRuntime,
  CheckpointState,
  CheckpointSupport
}
import edu.uci.ics.amber.util.VirtualIdentityUtils
import edu.uci.ics.amber.virtualidentity.ActorVirtualIdentity
import edu.uci.ics.amber.workflow.PortIdentity

class SerializationManager(val actorId: ActorVirtualIdentity) extends AmberLogging {

  @transient private var serializationCall: () => Unit = _
  private var execInitMsg: InitializeExecutorRequest = _

  def setOpInitialization(msg: InitializeExecutorRequest): Unit = {
    execInitMsg = msg
  }

  def restoreExecutorState(
      chkpt: CheckpointState
  ): (OperatorExecutor, Iterator[(TupleLike, Option[PortIdentity])]) = {
    val opExecInitInfo: OpExecInitInfo = AmberRuntime.serde
      .deserialize(execInitMsg.opExecInitInfo.value.toByteArray, classOf[OpExecInitInfo])
      .get
    val executor = generateJavaOpExec(
      opExecInitInfo,
      VirtualIdentityUtils.getWorkerIndex(actorId),
      execInitMsg.totalWorkerCount
    )
    val iter = executor match {
      case support: CheckpointSupport =>
        support.deserializeState(chkpt)
      case _ => Iterator.empty
    }
    (executor, iter)
  }

  def registerSerialization(call: () => Unit): Unit = {
    serializationCall = call
  }

  def applySerialization(): Unit = {
    if (serializationCall != null) {
      serializationCall()
      serializationCall = null
    }
  }

}

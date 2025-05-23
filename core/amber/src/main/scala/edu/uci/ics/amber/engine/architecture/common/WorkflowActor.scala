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

package edu.uci.ics.amber.engine.architecture.common

import akka.actor.{Actor, ActorRef, Address, Stash}
import akka.pattern.ask
import akka.util.Timeout
import edu.uci.ics.amber.clustering.ClusterListener.GetAvailableNodeAddresses
import edu.uci.ics.amber.engine.architecture.common.WorkflowActor._
import edu.uci.ics.amber.engine.architecture.logreplay.{
  ReplayLogGenerator,
  ReplayLogManager,
  ReplayLogRecord,
  ReplayOrderEnforcer
}
import edu.uci.ics.amber.engine.architecture.worker.WorkflowWorker.{
  FaultToleranceConfig,
  MainThreadDelegateMessage,
  StateRestoreConfig,
  TriggerSend
}
import edu.uci.ics.amber.engine.common.ambermessage.WorkflowFIFOMessage
import edu.uci.ics.amber.engine.common.storage.SequentialRecordStorage
import edu.uci.ics.amber.engine.common.{AmberLogging, CheckpointState}
import edu.uci.ics.amber.core.virtualidentity.{ActorVirtualIdentity, ChannelIdentity}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object WorkflowActor {

  /** Ack for NetworkMessage
    *
    * @param messageId    Long, id of the received network message
    * @param ackedCredit  Long, received size of the message, used to subtract sender's inflight credit
    * @param queuedCredit Long, receiver queue's size
    */
  final case class NetworkAck(messageId: Long, ackedCredit: Long, queuedCredit: Long)

  final case class MessageBecomesDeadLetter(message: NetworkMessage)

  /** Identifier <-> ActorRef related messages
    */
  final case class GetActorRef(id: ActorVirtualIdentity, replyTo: Set[ActorRef])

  final case class RegisterActorRef(id: ActorVirtualIdentity, ref: ActorRef)

  /** All outgoing message should be eventually NetworkMessage
    *
    * @param messageId       Long, id for a NetworkMessage, used for FIFO and ExactlyOnce
    * @param internalMessage WorkflowMessage, the message payload
    */
  final case class NetworkMessage(messageId: Long, internalMessage: WorkflowFIFOMessage)

  // sent from network communicator to next worker to poll for credit information
  final case class CreditRequest(channelId: ChannelIdentity)

  final case class CreditResponse(channelId: ChannelIdentity, credit: Long)
}

abstract class WorkflowActor(
    replayLogConfOpt: Option[FaultToleranceConfig],
    val actorId: ActorVirtualIdentity
) extends Actor
    with Stash
    with AmberLogging {

  //
  // Akka related components:
  //
  val actorService: AkkaActorService = new AkkaActorService(actorId, this.context)
  actorService.getAvailableNodeAddressesFunc = () => {
    implicit val timeout: Timeout = 5.seconds
    Await
      .result(
        context.actorSelection("/user/cluster-info") ? GetAvailableNodeAddresses(),
        5.seconds
      )
      .asInstanceOf[Array[Address]]
  }
  val actorRefMappingService: AkkaActorRefMappingService = new AkkaActorRefMappingService(
    actorService
  )
  actorRefMappingService.registerActorRef(actorId, self)
  val transferService: AkkaMessageTransferService =
    new AkkaMessageTransferService(actorService, actorRefMappingService, handleBackpressure)

  logger.info(s"worker replay log writing conf: $replayLogConfOpt")

  val logStorage: SequentialRecordStorage[ReplayLogRecord] =
    SequentialRecordStorage.getStorage(replayLogConfOpt.map(_.writeTo))
  val logManager: ReplayLogManager =
    ReplayLogManager.createLogManager(logStorage, getLogName, sendMessageFromLogWriterToActor)

  def getLogName: String = actorId.name.replace("Worker:", "")

  def sendMessageFromLogWriterToActor(
      msg: Either[MainThreadDelegateMessage, WorkflowFIFOMessage]
  ): Unit = {
    // limitation: TriggerSend will be processed after input messages before it.
    msg match {
      case Left(value)  => self ! value
      case Right(value) => self ! TriggerSend(value)
    }
  }

  def handleTriggerSend: Receive = {
    case TriggerSend(msg) =>
      transferService.send(msg)
  }

  def receiveActorRefRelatedMessages: Receive = {
    case GetActorRef(actorId, replyTo) =>
      actorRefMappingService.retrieveActorRef(actorId, replyTo)
    case RegisterActorRef(actorId, ref) =>
      actorRefMappingService.registerActorRef(actorId, ref)
  }

  // actor behavior for FIFO messages
  def receiveMessageAndAck: Receive = {
    case NetworkMessage(id, workflowMsg @ WorkflowFIFOMessage(channel, _, _)) =>
      actorRefMappingService.registerActorRef(channel.fromWorkerId, sender())
      try {
        handleInputMessage(id, workflowMsg)
      } catch {
        case e: Throwable =>
          logger.warn("actor failed due to exception", e)
          throw e
      }
    case NetworkAck(id, ackedCredit, queuedCredit) =>
      transferService.receiveAck(id, ackedCredit, queuedCredit)
  }

  def receiveCreditMessages: Receive = {
    case CreditRequest(channel) =>
      sender() ! CreditResponse(channel, getQueuedCredit(channel))
    case CreditResponse(channel, credit) =>
      transferService.updateChannelCreditFromReceiver(channel, credit)
  }

  def receiveDeadLetterMessage: Receive = {
    case MessageBecomesDeadLetter(msg) =>
      val dest = msg.internalMessage.channelId.toWorkerId
      if (dest == actorId) {
        actorService.scheduleOnce(
          100.millis,
          () => {
            logger.warn(s"sending message to self failed, retry sending $msg to self directly.")
            self ! msg
          }
        )
      } else {
        actorRefMappingService.removeActorRef(dest)
      }
  }

  def handleInputMessage(id: Long, workflowMsg: WorkflowFIFOMessage): Unit

  //
  //flow control:
  //
  def getQueuedCredit(channelId: ChannelIdentity): Long

  def handleBackpressure(isBackpressured: Boolean): Unit

  //
  //Actor lifecycle: Initialization
  //
  def initState(): Unit

  def loadFromCheckpoint(chkpt: CheckpointState): Unit

  def setupReplay(
      amberProcessor: AmberProcessor,
      stateRestoreConf: StateRestoreConfig,
      onComplete: () => Unit
  ): Unit = {
    val logStorageToRead =
      SequentialRecordStorage.getStorage[ReplayLogRecord](Some(stateRestoreConf.readFrom))
    val replayTo = stateRestoreConf.replayDestination
    if (logStorageToRead.containsFolder(replayTo.toString)) {
      // checkpoint found
      val chkptStorage = SequentialRecordStorage.getStorage[CheckpointState](
        Some(stateRestoreConf.readFrom.resolve(replayTo.toString))
      )
      val chkpt = chkptStorage.getReader(getLogName).mkRecordIterator().next()
      loadFromCheckpoint(chkpt)
    } else {
      // do replay from scratch
      val (processSteps, messages) =
        ReplayLogGenerator.generate(logStorageToRead, getLogName, replayTo)
      logger.info(
        s"setting up replay, " +
          s"read from ${stateRestoreConf.readFrom} " +
          s"current step = ${logManager.getStep} " +
          s"target step = $replayTo " +
          s"# of log record to replay = ${processSteps.size}"
      )
      val orderEnforcer = new ReplayOrderEnforcer(
        logManager,
        processSteps,
        startStep = logManager.getStep,
        onComplete
      )
      amberProcessor.inputGateway.addEnforcer(orderEnforcer)
      messages.foreach(message =>
        amberProcessor.inputGateway.getChannel(message.channelId).acceptMessage(message)
      )
    }
  }

  override def preStart(): Unit = {
    try {
      transferService.initialize()
      initState()
      context.parent ! RegisterActorRef(actorId, context.self)
    } catch {
      case t: Throwable =>
        logger.warn("actor initialization failed due to exception", t)
        throw t
    }
  }

  override def receive: Receive = {
    receiveActorRefRelatedMessages orElse
      handleTriggerSend orElse
      receiveMessageAndAck orElse
      receiveCreditMessages orElse
      receiveDeadLetterMessage
  }

  override def postStop(): Unit = {
    transferService.stop()
  }

}

package edu.uci.ics.amber.engine.architecture.logreplay

import edu.uci.ics.amber.engine.architecture.common.ProcessingStepCursor.INIT_STEP
import edu.uci.ics.amber.engine.common.ambermessage.{ChannelID, WorkflowFIFOMessage}

import scala.collection.mutable

class ReplayLoggerImpl extends ReplayLogger {

  private val tempLogs = mutable.ArrayBuffer[ReplayLogRecord]()

  private var currentChannel: ChannelID = _

  private var lastStep = INIT_STEP

  /**
    * Records the current processing step along with an associated message.
    * This method also monitors the channel information. If the new channel matches the last recorded channel
    * and there is no associated message for this step, the logging operation is bypassed.
    * Otherwise, it appends a ProcessingStep log record with the message content, provided the message exists.
    *
    * @param step The current processing step.
    * @param channel The channel ID associated with the processing step.
    * @param message An optional message associated with the processing step.
    */
  override def logCurrentStepWithMessage(
      step: Long,
      channel: ChannelID,
      message: Option[WorkflowFIFOMessage]
  ): Unit = {
    if (currentChannel == channel && message.isEmpty) {
      return
    }
    currentChannel = channel
    lastStep = step
    tempLogs.append(ProcessingStep(channel, step))
    if (message.isDefined) {
      tempLogs.append(MessageContent(message.get))
    }
  }

  /**
    * Called when the data processor attempts to output a message.
    * This method retrieves all accumulated log records and passes them to the writer thread for persistence.
    * It ensures the processing up to the current processing step is captured in the log records.
    *
    * @param step The current processing step.
    * @return An array of ReplayLogRecord containing all the log records up to the current step.
    */
  def drainCurrentLogRecords(step: Long): Array[ReplayLogRecord] = {
    if (lastStep != step) {
      lastStep = step
      tempLogs.append(ProcessingStep(currentChannel, step))
    }
    val result = tempLogs.toArray
    tempLogs.clear()
    result
  }
}
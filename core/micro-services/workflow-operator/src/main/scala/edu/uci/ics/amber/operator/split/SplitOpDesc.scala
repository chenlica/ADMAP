package edu.uci.ics.amber.operator.split

import com.fasterxml.jackson.annotation.{JsonProperty, JsonPropertyDescription}
import com.google.common.base.Preconditions
import edu.uci.ics.amber.core.executor.OpExecInitInfo
import edu.uci.ics.amber.core.tuple.Schema
import edu.uci.ics.amber.core.workflow.{PhysicalOp, SchemaPropagationFunc}
import edu.uci.ics.amber.operator.LogicalOp
import edu.uci.ics.amber.operator.metadata.OperatorInfo
import edu.uci.ics.amber.operator.metadata.OperatorGroupConstants
import edu.uci.ics.amber.virtualidentity.{ExecutionIdentity, WorkflowIdentity}
import edu.uci.ics.amber.workflow.{InputPort, OutputPort, PortIdentity}

import scala.util.Random

class SplitOpDesc extends LogicalOp {

  @JsonProperty(value = "training percentage", required = false, defaultValue = "80")
  @JsonPropertyDescription("percentage of training split data (default 80%)")
  var k: Int = 80

  @JsonProperty(value = "random seed", required = false)
  @JsonPropertyDescription("Random seed for split")
  var seed: Int = Random.nextInt()

  override def getPhysicalOp(
      workflowId: WorkflowIdentity,
      executionId: ExecutionIdentity
  ): PhysicalOp = {
    PhysicalOp
      .oneToOnePhysicalOp(
        workflowId,
        executionId,
        operatorIdentifier,
        OpExecInitInfo((_, _) => new SplitOpExec(k, seed))
      )
      .withInputPorts(operatorInfo.inputPorts)
      .withOutputPorts(operatorInfo.outputPorts)
      .withParallelizable(false)
      .withPropagateSchema(
        SchemaPropagationFunc(inputSchemas =>
          operatorInfo.outputPorts
            .map(_.id)
            .map(id => id -> inputSchemas(operatorInfo.inputPorts.head.id))
            .toMap
        )
      )
  }

  override def operatorInfo: OperatorInfo = {
    OperatorInfo(
      userFriendlyName = "Training/Testing Split",
      operatorDescription = "Split training and testing data to two different ports",
      operatorGroupName = OperatorGroupConstants.MACHINE_LEARNING_GROUP,
      inputPorts = List(InputPort()),
      outputPorts = List(
        OutputPort(PortIdentity(), displayName = "training"),
        OutputPort(PortIdentity(1), displayName = "testing")
      ),
      dynamicInputPorts = true,
      dynamicOutputPorts = true
    )
  }

  override def getOutputSchema(schemas: Array[Schema]): Schema = throw new NotImplementedError()

  override def getOutputSchemas(schemas: Array[Schema]): Array[Schema] = {
    Preconditions.checkArgument(schemas.length == 1)
    Array(schemas(0), schemas(0))
  }
}

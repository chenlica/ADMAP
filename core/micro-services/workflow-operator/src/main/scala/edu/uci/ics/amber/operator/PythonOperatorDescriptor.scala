package edu.uci.ics.amber.operator

import edu.uci.ics.amber.core.executor.OpExecInitInfoWithCode
import edu.uci.ics.amber.core.workflow.{PhysicalOp, SchemaPropagationFunc}
import edu.uci.ics.amber.virtualidentity.{ExecutionIdentity, WorkflowIdentity}

trait PythonOperatorDescriptor extends LogicalOp {
  override def getPhysicalOp(
      workflowId: WorkflowIdentity,
      executionId: ExecutionIdentity
  ): PhysicalOp = {
    val opExecInitInfo = OpExecInitInfoWithCode((_, _) => (generatePythonCode(), "python"))

    val physicalOp = if (asSource()) {
      PhysicalOp.sourcePhysicalOp(
        workflowId,
        executionId,
        operatorIdentifier,
        opExecInitInfo
      )
    } else {
      PhysicalOp.oneToOnePhysicalOp(
        workflowId,
        executionId,
        operatorIdentifier,
        opExecInitInfo
      )
    }

    physicalOp
      .withInputPorts(operatorInfo.inputPorts)
      .withOutputPorts(operatorInfo.outputPorts)
      .withParallelizable(parallelizable())
      .withPropagateSchema(
        SchemaPropagationFunc(inputSchemas =>
          Map(
            operatorInfo.outputPorts.head.id -> getOutputSchema(
              operatorInfo.inputPorts.map(_.id).map(inputSchemas(_)).toArray
            )
          )
        )
      )
  }

  def parallelizable(): Boolean = false

  def asSource(): Boolean = false

  /**
    * This method is to be implemented to generate the actual Python source code
    * based on operators predicates.
    *
    * @return a String representation of the executable Python source code.
    */
  def generatePythonCode(): String

}

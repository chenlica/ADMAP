package edu.uci.ics.texera.workflow.operators.visualization.htmlviz

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle
import edu.uci.ics.amber.engine.architecture.deploysemantics.layer.OpExecInitInfo
import edu.uci.ics.amber.engine.common.model.{PhysicalOp, SchemaPropagationFunc}
import edu.uci.ics.amber.engine.common.model.tuple.{Attribute, AttributeType, Schema}
import edu.uci.ics.amber.engine.common.virtualidentity.{ExecutionIdentity, WorkflowIdentity}
import edu.uci.ics.texera.workflow.common.metadata.annotations.AutofillAttributeName
import edu.uci.ics.texera.workflow.common.metadata.{OperatorGroupConstants, OperatorInfo}
import edu.uci.ics.amber.engine.common.workflow.{InputPort, OutputPort}
import edu.uci.ics.texera.workflow.operators.visualization.{
  VisualizationConstants,
  VisualizationOperator
}

/**
  * HTML Visualization operator to render any given HTML code
  * This is the description of the operator
  */
class HtmlVizOpDesc extends VisualizationOperator {
  @JsonProperty(required = true)
  @JsonSchemaTitle("HTML content")
  @AutofillAttributeName var htmlContentAttrName: String = _

  override def chartType: String = VisualizationConstants.HTML_VIZ

  override def getPhysicalOp(
      workflowId: WorkflowIdentity,
      executionId: ExecutionIdentity
  ): PhysicalOp = {
    PhysicalOp
      .oneToOnePhysicalOp(
        workflowId,
        executionId,
        operatorIdentifier,
        OpExecInitInfo((_, _) => new HtmlVizOpExec(htmlContentAttrName))
      )
      .withInputPorts(operatorInfo.inputPorts)
      .withOutputPorts(operatorInfo.outputPorts)
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

  override def operatorInfo: OperatorInfo =
    OperatorInfo(
      "HTML visualizer",
      "Render the result of HTML content",
      OperatorGroupConstants.VISUALIZATION_GROUP,
      inputPorts = List(InputPort()),
      outputPorts = List(OutputPort())
    )

  override def getOutputSchema(schemas: Array[Schema]): Schema =
    Schema.builder().add(new Attribute("html-content", AttributeType.STRING)).build()
}

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

package edu.uci.ics.amber.operator.visualization.urlviz

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.{JsonSchemaInject, JsonSchemaTitle}
import edu.uci.ics.amber.core.executor.OpExecWithClassName
import edu.uci.ics.amber.core.tuple.{AttributeType, Schema}
import edu.uci.ics.amber.core.workflow.{InputPort, OutputPort, PhysicalOp, SchemaPropagationFunc}
import edu.uci.ics.amber.operator.LogicalOp
import edu.uci.ics.amber.core.virtualidentity.{ExecutionIdentity, WorkflowIdentity}
import edu.uci.ics.amber.operator.metadata.{OperatorGroupConstants, OperatorInfo}
import edu.uci.ics.amber.operator.metadata.annotations.AutofillAttributeName
import edu.uci.ics.amber.util.JSONUtils.objectMapper
import edu.uci.ics.amber.core.workflow.OutputPort.OutputMode

/**
  * URL Visualization operator to render any content in given URL link
  * This is the description of the operator
  */
@JsonSchemaInject(json = """
 {
   "attributeTypeRules": {
     "urlContentAttrName": {
       "enum": ["string"]
     }
   }
 }
 """)
class UrlVizOpDesc extends LogicalOp {

  @JsonProperty(required = true)
  @JsonSchemaTitle("URL content")
  @AutofillAttributeName
  val urlContentAttrName: String = ""

  override def getPhysicalOp(
      workflowId: WorkflowIdentity,
      executionId: ExecutionIdentity
  ): PhysicalOp = {
    PhysicalOp
      .manyToOnePhysicalOp(
        workflowId,
        executionId,
        operatorIdentifier,
        OpExecWithClassName(
          "edu.uci.ics.amber.operator.visualization.urlviz.UrlVizOpExec",
          objectMapper.writeValueAsString(this)
        )
      )
      .withInputPorts(operatorInfo.inputPorts)
      .withOutputPorts(operatorInfo.outputPorts)
      .withPropagateSchema(
        SchemaPropagationFunc(_ => {
          val outputSchema = Schema().add("html-content", AttributeType.STRING)
          Map(operatorInfo.outputPorts.head.id -> outputSchema)
        })
      )
  }

  override def operatorInfo: OperatorInfo =
    OperatorInfo(
      "URL Visualizer",
      "Render the content of URL",
      OperatorGroupConstants.VISUALIZATION_MEDIA_GROUP,
      inputPorts = List(InputPort()),
      outputPorts = List(OutputPort(mode = OutputMode.SINGLE_SNAPSHOT))
    )

}

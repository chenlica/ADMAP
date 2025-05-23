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

package edu.uci.ics.amber.operator.visualization.histogram

import com.fasterxml.jackson.annotation.{JsonProperty, JsonPropertyDescription}
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle
import edu.uci.ics.amber.core.tuple.{AttributeType, Schema}
import edu.uci.ics.amber.core.workflow.OutputPort.OutputMode
import edu.uci.ics.amber.core.workflow.{InputPort, OutputPort, PortIdentity}
import edu.uci.ics.amber.operator.metadata.{OperatorGroupConstants, OperatorInfo}
import edu.uci.ics.amber.operator.metadata.annotations.AutofillAttributeName
import edu.uci.ics.amber.operator.PythonOperatorDescriptor
class HistogramChartOpDesc extends PythonOperatorDescriptor {
  @JsonProperty(value = "value", required = true)
  @JsonSchemaTitle("Value Column")
  @JsonPropertyDescription("Column for counting values.")
  @AutofillAttributeName
  var value: String = ""

  @JsonProperty(required = false)
  @JsonSchemaTitle("Color Column")
  @JsonPropertyDescription("Column for differentiating data by its value.")
  @AutofillAttributeName
  var color: String = ""

  @JsonProperty(required = false)
  @JsonSchemaTitle("SeparateBy Column")
  @JsonPropertyDescription("Column for separating histogram chart by its value.")
  @AutofillAttributeName
  var separateBy: String = ""

  @JsonProperty(required = false, defaultValue = "")
  @JsonSchemaTitle("Distribution Type")
  @JsonPropertyDescription("Distribution type (rug, box, violin).")
  var marginal: String = ""

  @JsonProperty(required = false)
  @JsonSchemaTitle("Pattern")
  @JsonPropertyDescription("Add texture to the chart based on an attribute")
  @AutofillAttributeName
  var pattern: String = ""

  override def operatorInfo: OperatorInfo =
    OperatorInfo(
      "Histogram",
      "Visualize data in a Histogram Chart",
      OperatorGroupConstants.VISUALIZATION_STATISTICAL_GROUP,
      inputPorts = List(InputPort()),
      outputPorts = List(OutputPort(mode = OutputMode.SINGLE_SNAPSHOT))
    )

  def createPlotlyFigure(): String = {
    assert(value.nonEmpty)
    var colorParam = ""
    var categoryParam = ""
    var marginalParam = ""
    var patternParam = ""
    if (color.nonEmpty) colorParam = s", color = '$color'"
    if (separateBy.nonEmpty) categoryParam = s", facet_col = '$separateBy'"
    if (marginal.nonEmpty) marginalParam = s", marginal='$marginal'"
    if (pattern != "") patternParam = s", pattern_shape='$pattern'"

    s"""
       |        fig = px.histogram(table, x = '$value', text_auto = True $colorParam $categoryParam $marginalParam $patternParam)
       |        fig.update_layout(margin=dict(l=0, r=0, t=0, b=0))
       |""".stripMargin
  }

  override def generatePythonCode(): String = {
    val finalCode =
      s"""
         |from pytexera import *
         |
         |import plotly.express as px
         |import plotly.graph_objects as go
         |import plotly.io
         |import numpy as np
         |
         |class ProcessTableOperator(UDFTableOperator):
         |    def render_error(self, error_msg):
         |        return '''<h1>Histogram chart is not available.</h1>
         |                  <p>Reason is: {} </p>
         |               '''.format(error_msg)
         |
         |    @overrides
         |    def process_table(self, table: Table, port: int) -> Iterator[Optional[TableLike]]:
         |        if table.empty:
         |           yield {'html-content': self.render_error("input table is empty.")}
         |           return
         |        ${createPlotlyFigure()}
         |        # convert fig to html content
         |        html = plotly.io.to_html(fig, include_plotlyjs='cdn', auto_play=False)
         |        yield {'html-content': html}
         |
         |""".stripMargin
    finalCode
  }

  override def getOutputSchemas(
      inputSchemas: Map[PortIdentity, Schema]
  ): Map[PortIdentity, Schema] = {
    val outputSchema = Schema()
      .add("html-content", AttributeType.STRING)
    Map(operatorInfo.outputPorts.head.id -> outputSchema)
    Map(operatorInfo.outputPorts.head.id -> outputSchema)
  }

}

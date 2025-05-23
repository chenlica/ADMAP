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

package edu.uci.ics.amber.operator.visualization.figureFactoryTable

import com.fasterxml.jackson.annotation.{JsonProperty, JsonPropertyDescription}
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle
import edu.uci.ics.amber.core.tuple.{AttributeType, Schema}
import edu.uci.ics.amber.operator.PythonOperatorDescriptor
import edu.uci.ics.amber.operator.metadata.{OperatorGroupConstants, OperatorInfo}
import edu.uci.ics.amber.core.workflow.OutputPort.OutputMode
import edu.uci.ics.amber.core.workflow.{InputPort, OutputPort, PortIdentity}
class FigureFactoryTableOpDesc extends PythonOperatorDescriptor {

  @JsonProperty(required = false)
  @JsonSchemaTitle("Font Size")
  @JsonPropertyDescription("Font size of the Figure Factory Table")
  var fontSize: String = "12"

  @JsonProperty(required = false)
  @JsonSchemaTitle("Font Color (Hex Code)")
  @JsonPropertyDescription("Font color of the Figure Factory Table")
  var fontColor: String = "#000000"

  @JsonProperty(required = false)
  @JsonSchemaTitle("Row Height")
  @JsonPropertyDescription("Row height of the Figure Factory Table")
  var rowHeight: String = "30"

  @JsonPropertyDescription("List of columns to include in the figure factory table")
  @JsonProperty(value = "add attribute", required = true)
  var columns: List[FigureFactoryTableConfig] = List()

  private def getAttributes: String =
    columns.map(_.attributeName).mkString("'", "','", "'")

  def manipulateTable(): String = {
    assert(columns.nonEmpty)

    val attributes = getAttributes
    s"""
       |        # drops rows with missing values pertaining to relevant columns
       |        table = table.dropna(subset=[$attributes])
       |
       |""".stripMargin
  }

  def createFigureFactoryTablePlotlyFigure(): String = {
    assert(columns.nonEmpty)

    val intFontSize: Option[Double] = fontSize.toDoubleOption
    val intRowHeight: Option[Double] = rowHeight.toDoubleOption

    assert(intFontSize.isDefined && intFontSize.get >= 0)
    assert(intRowHeight.isDefined && intRowHeight.get >= 30)

    val attributes = getAttributes
    s"""
       |        filtered_table = table[[$attributes]]
       |        headers = filtered_table.columns.tolist()
       |        cell_values = [filtered_table[col].tolist() for col in headers]
       |
       |        data = [headers] + list(map(list, zip(*cell_values)))
       |        fig = ff.create_table(data, height_constant = ${intRowHeight.get}, font_colors=['$fontColor'])
       |
       |        # Make text size larger
       |        for i in range(len(fig.layout.annotations)):
       |          fig.layout.annotations[i].font.size = ${intFontSize.get}
       |
       |""".stripMargin
  }

  override def generatePythonCode(): String = {
    s"""
       |from pytexera import *
       |import plotly.figure_factory as ff
       |import plotly.io
       |
       |class TableChartOperator(UDFTableOperator):
       |
       |    def process_table(self, table: Table, port: int) -> Iterator[Optional[TableLike]]:
       |
       |        if table.empty:
       |           yield {'html-content': self.render_error("input table is empty.")}
       |           return
       |
       |        ${manipulateTable()}
       |
       |        if table.empty:
       |           yield {'html-content': self.render_error("value column contains only non-positive numbers or nulls.")}
       |           return
       |
       |        ${createFigureFactoryTablePlotlyFigure()}
       |        fig.update_layout(margin=dict(l=0, r=0, b=0, t=0))
       |        html_content = plotly.io.to_html(fig, include_plotlyjs='cdn', include_mathjax='cdn')
       |        yield {'html-content': html_content}
  """.stripMargin
  }

  override def operatorInfo: OperatorInfo = {
    OperatorInfo(
      "Figure Factory Table",
      "Visualize data in a figure factory table",
      OperatorGroupConstants.VISUALIZATION_BASIC_GROUP,
      inputPorts = List(InputPort()),
      outputPorts = List(OutputPort(mode = OutputMode.SINGLE_SNAPSHOT))
    )
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

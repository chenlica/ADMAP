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

package edu.uci.ics.amber.operator.huggingFace

import com.fasterxml.jackson.annotation.{JsonProperty, JsonPropertyDescription}
import edu.uci.ics.amber.core.tuple.{AttributeType, Schema}
import edu.uci.ics.amber.core.workflow.{InputPort, OutputPort, PortIdentity}
import edu.uci.ics.amber.operator.PythonOperatorDescriptor
import edu.uci.ics.amber.operator.metadata.{OperatorGroupConstants, OperatorInfo}
import edu.uci.ics.amber.operator.metadata.annotations.AutofillAttributeName
class HuggingFaceSentimentAnalysisOpDesc extends PythonOperatorDescriptor {
  @JsonProperty(value = "attribute", required = true)
  @JsonPropertyDescription("column to perform sentiment analysis on")
  @AutofillAttributeName
  var attribute: String = _

  @JsonProperty(
    value = "Positive result attribute",
    required = true,
    defaultValue = "huggingface_sentiment_positive"
  )
  @JsonPropertyDescription("column name of the sentiment analysis result (positive)")
  var resultAttributePositive: String = _

  @JsonProperty(
    value = "Neutral result attribute",
    required = true,
    defaultValue = "huggingface_sentiment_neutral"
  )
  @JsonPropertyDescription("column name of the sentiment analysis result (neutral)")
  var resultAttributeNeutral: String = _

  @JsonProperty(
    value = "Negative result attribute",
    required = true,
    defaultValue = "huggingface_sentiment_negative"
  )
  @JsonPropertyDescription("column name of the sentiment analysis result (negative)")
  var resultAttributeNegative: String = _

  override def generatePythonCode(): String = {
    s"""from pytexera import *
       |from transformers import pipeline
       |from transformers import AutoModelForSequenceClassification
       |from transformers import TFAutoModelForSequenceClassification
       |from transformers import AutoTokenizer, AutoConfig
       |import numpy as np
       |from scipy.special import softmax
       |
       |class ProcessTupleOperator(UDFOperatorV2):
       |
       |    def open(self):
       |        model_name = "cardiffnlp/twitter-roberta-base-sentiment-latest"
       |        self.tokenizer = AutoTokenizer.from_pretrained(model_name)
       |        self.config = AutoConfig.from_pretrained(model_name)
       |        self.model = AutoModelForSequenceClassification.from_pretrained(model_name)
       |
       |    @overrides
       |    def process_tuple(self, tuple_: Tuple, port: int) -> Iterator[Optional[TupleLike]]:
       |        encoded_input = self.tokenizer(tuple_["$attribute"], return_tensors='pt')
       |        output = self.model(**encoded_input)
       |        scores = softmax(output[0][0].detach().numpy())
       |        ranking = np.argsort(scores)[::-1]
       |        labels = {"positive": "$resultAttributePositive", "neutral": "$resultAttributeNeutral", "negative": "$resultAttributeNegative"}
       |        for i in range(scores.shape[0]):
       |            label = labels[self.config.id2label[ranking[i]]]
       |            score = scores[ranking[i]]
       |            tuple_[label] = np.round(float(score), 4)
       |        yield tuple_""".stripMargin
  }

  override def operatorInfo: OperatorInfo =
    OperatorInfo(
      "Hugging Face Sentiment Analysis",
      "Analyzing Sentiments with a Twitter-Based Model from Hugging Face",
      OperatorGroupConstants.HUGGINGFACE_GROUP,
      inputPorts = List(InputPort()),
      outputPorts = List(OutputPort()),
      supportReconfiguration = true
    )

  override def getOutputSchemas(
      inputSchemas: Map[PortIdentity, Schema]
  ): Map[PortIdentity, Schema] = {
    if (
      resultAttributePositive == null || resultAttributePositive.trim.isEmpty ||
      resultAttributeNeutral == null || resultAttributeNeutral.trim.isEmpty ||
      resultAttributeNegative == null || resultAttributeNegative.trim.isEmpty
    )
      return null
    Map(
      operatorInfo.outputPorts.head.id -> inputSchemas(operatorInfo.inputPorts.head.id)
        .add(resultAttributePositive, AttributeType.DOUBLE)
        .add(resultAttributeNeutral, AttributeType.DOUBLE)
        .add(resultAttributeNegative, AttributeType.DOUBLE)
    )
  }
}

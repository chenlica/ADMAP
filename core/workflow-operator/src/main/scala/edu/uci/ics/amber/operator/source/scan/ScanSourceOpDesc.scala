package edu.uci.ics.amber.operator.source.scan

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonProperty, JsonPropertyDescription}
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle
import edu.uci.ics.amber.core.tuple.Schema
import edu.uci.ics.amber.core.workflow.WorkflowContext
import edu.uci.ics.amber.operator.metadata.{OperatorGroupConstants, OperatorInfo}
import edu.uci.ics.amber.operator.source.SourceOperatorDescriptor
import edu.uci.ics.amber.workflow.OutputPort
import org.apache.commons.lang3.builder.EqualsBuilder

import java.net.URI

abstract class ScanSourceOpDesc extends SourceOperatorDescriptor {

  /** in the case we do not want to read the entire large file, but only
    * the first a few lines of it to do the type inference.
    */
  @JsonIgnore
  var INFER_READ_LIMIT: Int = 100

  @JsonProperty(required = true)
  @JsonSchemaTitle("File")
  @JsonDeserialize(contentAs = classOf[java.lang.String])
  var fileName: Option[String] = None

  @JsonProperty(defaultValue = "UTF_8", required = true)
  @JsonSchemaTitle("File Encoding")
  @JsonPropertyDescription("decoding charset to use on input")
  var fileEncoding: FileDecodingMethod = FileDecodingMethod.UTF_8

  // uri of the file
  @JsonIgnore
  var fileUri: Option[String] = None

  @JsonIgnore
  var fileTypeName: Option[String] = None

  @JsonProperty()
  @JsonSchemaTitle("Limit")
  @JsonPropertyDescription("max output count")
  @JsonDeserialize(contentAs = classOf[Int])
  var limit: Option[Int] = None

  @JsonProperty()
  @JsonSchemaTitle("Offset")
  @JsonPropertyDescription("starting point of output")
  @JsonDeserialize(contentAs = classOf[Int])
  var offset: Option[Int] = None

  override def sourceSchema(): Schema = {
    if (fileUri.isEmpty) return null
    inferSchema()
  }

  override def setContext(workflowContext: WorkflowContext): Unit = {
    super.setContext(workflowContext)
  }

  override def operatorInfo: OperatorInfo = {
    OperatorInfo(
      userFriendlyName = s"${fileTypeName.getOrElse("Unknown")} File Scan",
      operatorDescription = s"Scan data from a ${fileTypeName.getOrElse("Unknown")} file",
      OperatorGroupConstants.INPUT_GROUP,
      inputPorts = List.empty,
      outputPorts = List(OutputPort())
    )
  }

  def inferSchema(): Schema

  def setFileUri(uri: URI): Unit = {
    fileUri = Some(uri.toASCIIString)
  }

  override def equals(that: Any): Boolean =
    EqualsBuilder.reflectionEquals(this, that, "context", "fileHandle")
}

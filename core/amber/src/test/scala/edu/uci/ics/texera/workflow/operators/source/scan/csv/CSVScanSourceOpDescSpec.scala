package edu.uci.ics.texera.workflow.operators.source.scan.csv

import edu.uci.ics.amber.engine.common.model.WorkflowContext
import edu.uci.ics.amber.engine.common.model.tuple.{AttributeType, Schema}
import edu.uci.ics.amber.engine.common.workflow.PortIdentity
import WorkflowContext.{DEFAULT_EXECUTION_ID, DEFAULT_WORKFLOW_ID}
import edu.uci.ics.texera.workflow.common.storage.FileResolver
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec

class CSVScanSourceOpDescSpec extends AnyFlatSpec with BeforeAndAfter {

  val workflowContext = new WorkflowContext()
  var csvScanSourceOpDesc: CSVScanSourceOpDesc = _
  var parallelCsvScanSourceOpDesc: ParallelCSVScanSourceOpDesc = _
  before {
    csvScanSourceOpDesc = new CSVScanSourceOpDesc()
    csvScanSourceOpDesc.outputPortToSchemaMapping(PortIdentity()) =
      csvScanSourceOpDesc.getOutputSchema(Array())
    parallelCsvScanSourceOpDesc = new ParallelCSVScanSourceOpDesc()
    parallelCsvScanSourceOpDesc.outputPortToSchemaMapping(PortIdentity()) =
      parallelCsvScanSourceOpDesc.getOutputSchema(Array())
  }

  it should "infer schema from single-line-data csv" in {

    parallelCsvScanSourceOpDesc.fileName = Some("src/test/resources/country_sales_small.csv")
    parallelCsvScanSourceOpDesc.customDelimiter = Some(",")
    parallelCsvScanSourceOpDesc.hasHeader = true
    parallelCsvScanSourceOpDesc.setContext(workflowContext)
    parallelCsvScanSourceOpDesc.setFileUri(
      FileResolver.resolve(parallelCsvScanSourceOpDesc.fileName.get)
    )
    val inferredSchema: Schema = parallelCsvScanSourceOpDesc.inferSchema()

    assert(inferredSchema.getAttributes.length == 14)
    assert(inferredSchema.getAttribute("Order ID").getType == AttributeType.INTEGER)
    assert(inferredSchema.getAttribute("Unit Price").getType == AttributeType.DOUBLE)

  }

  it should "infer schema from headerless single-line-data csv" in {

    parallelCsvScanSourceOpDesc.fileName =
      Some("src/test/resources/country_sales_headerless_small.csv")
    parallelCsvScanSourceOpDesc.customDelimiter = Some(",")
    parallelCsvScanSourceOpDesc.hasHeader = false
    parallelCsvScanSourceOpDesc.setContext(workflowContext)
    parallelCsvScanSourceOpDesc.setFileUri(
      FileResolver.resolve(parallelCsvScanSourceOpDesc.fileName.get)
    )

    val inferredSchema: Schema = parallelCsvScanSourceOpDesc.inferSchema()

    assert(inferredSchema.getAttributes.length == 14)
    assert(inferredSchema.getAttribute("column-10").getType == AttributeType.DOUBLE)
    assert(inferredSchema.getAttribute("column-7").getType == AttributeType.INTEGER)
  }

  it should "infer schema from multi-line-data csv" in {

    csvScanSourceOpDesc.fileName = Some("src/test/resources/country_sales_small_multi_line.csv")
    csvScanSourceOpDesc.customDelimiter = Some(",")
    csvScanSourceOpDesc.hasHeader = true
    csvScanSourceOpDesc.setContext(workflowContext)
    csvScanSourceOpDesc.setFileUri(FileResolver.resolve(csvScanSourceOpDesc.fileName.get))

    val inferredSchema: Schema = csvScanSourceOpDesc.inferSchema()

    assert(inferredSchema.getAttributes.length == 14)
    assert(inferredSchema.getAttribute("Order ID").getType == AttributeType.INTEGER)
    assert(inferredSchema.getAttribute("Unit Price").getType == AttributeType.DOUBLE)
  }

  it should "infer schema from headerless multi-line-data csv" in {

    csvScanSourceOpDesc.fileName = Some("src/test/resources/country_sales_headerless_small.csv")
    csvScanSourceOpDesc.customDelimiter = Some(",")
    csvScanSourceOpDesc.hasHeader = false
    csvScanSourceOpDesc.setContext(workflowContext)
    csvScanSourceOpDesc.setFileUri(FileResolver.resolve(csvScanSourceOpDesc.fileName.get))

    val inferredSchema: Schema = csvScanSourceOpDesc.inferSchema()

    assert(inferredSchema.getAttributes.length == 14)
    assert(inferredSchema.getAttribute("column-10").getType == AttributeType.DOUBLE)
    assert(inferredSchema.getAttribute("column-7").getType == AttributeType.INTEGER)
  }

  it should "infer schema from headerless multi-line-data csv with custom delimiter" in {

    csvScanSourceOpDesc.fileName =
      Some("src/test/resources/country_sales_headerless_small_multi_line_custom_delimiter.csv")
    csvScanSourceOpDesc.customDelimiter = Some(";")
    csvScanSourceOpDesc.hasHeader = false
    csvScanSourceOpDesc.setContext(workflowContext)
    csvScanSourceOpDesc.setFileUri(FileResolver.resolve(csvScanSourceOpDesc.fileName.get))

    val inferredSchema: Schema = csvScanSourceOpDesc.inferSchema()

    assert(inferredSchema.getAttributes.length == 14)
    assert(inferredSchema.getAttribute("column-10").getType == AttributeType.DOUBLE)
    assert(inferredSchema.getAttribute("column-7").getType == AttributeType.INTEGER)
  }

  it should "create one worker with multi-line-data csv" in {

    csvScanSourceOpDesc.fileName =
      Some("src/test/resources/country_sales_headerless_small_multi_line_custom_delimiter.csv")
    csvScanSourceOpDesc.customDelimiter = Some(";")
    csvScanSourceOpDesc.hasHeader = false
    csvScanSourceOpDesc.setContext(workflowContext)
    csvScanSourceOpDesc.setFileUri(FileResolver.resolve(csvScanSourceOpDesc.fileName.get))

    assert(
      !csvScanSourceOpDesc
        .getPhysicalOp(DEFAULT_WORKFLOW_ID, DEFAULT_EXECUTION_ID)
        .parallelizable
    )
  }

}

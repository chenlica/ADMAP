package edu.uci.ics.texera.workflow.operators.source.scan.csv

import com.univocity.parsers.csv.{CsvFormat, CsvParser, CsvParserSettings}
import edu.uci.ics.amber.engine.common.executor.SourceOperatorExecutor
import edu.uci.ics.amber.engine.common.workflow.PortIdentity
import edu.uci.ics.amber.engine.common.{CheckpointState, CheckpointSupport}
import edu.uci.ics.amber.engine.common.model.tuple.{AttributeTypeUtils, Schema, TupleLike}
import edu.uci.ics.amber.engine.common.storage.DocumentFactory
import edu.uci.ics.texera.workflow.operators.source.scan.FileDecodingMethod

import java.io.InputStreamReader
import java.net.URI
import scala.collection.immutable.ArraySeq

class CSVScanSourceOpExec private[csv] (
    fileUri: String,
    fileEncoding: FileDecodingMethod,
    limit: Option[Int],
    offset: Option[Int],
    customDelimiter: Option[String],
    hasHeader: Boolean,
    schemaFunc: () => Schema
) extends SourceOperatorExecutor
    with CheckpointSupport {
  var inputReader: InputStreamReader = _
  var parser: CsvParser = _
  var schema: Schema = _
  var nextRow: Array[String] = _
  var numRowGenerated = 0

  override def produceTuple(): Iterator[TupleLike] = {

    val rowIterator = new Iterator[Array[String]] {
      override def hasNext: Boolean = {
        if (nextRow != null) {
          return true
        }
        nextRow = parser.parseNext()
        nextRow != null
      }

      override def next(): Array[String] = {
        val ret = nextRow
        numRowGenerated += 1
        nextRow = null
        ret
      }
    }

    var tupleIterator = rowIterator
      .drop(offset.getOrElse(0))
      .map(row => {
        try {
          TupleLike(
            ArraySeq.unsafeWrapArray(
              AttributeTypeUtils.parseFields(row.asInstanceOf[Array[Any]], schema)
            ): _*
          )
        } catch {
          case _: Throwable => null
        }
      })
      .filter(t => t != null)

    if (limit.isDefined) tupleIterator = tupleIterator.take(limit.get)

    tupleIterator
  }

  override def open(): Unit = {
    inputReader = new InputStreamReader(
      DocumentFactory.newReadonlyDocument(new URI(fileUri)).asInputStream(),
      fileEncoding.getCharset
    )

    val csvFormat = new CsvFormat()
    csvFormat.setDelimiter(customDelimiter.get.charAt(0))
    csvFormat.setLineSeparator("\n")
    csvFormat.setComment(
      '\u0000'
    ) // disable skipping lines starting with # (default comment character)
    val csvSetting = new CsvParserSettings()
    csvSetting.setMaxCharsPerColumn(-1)
    csvSetting.setFormat(csvFormat)
    csvSetting.setHeaderExtractionEnabled(hasHeader)

    parser = new CsvParser(csvSetting)
    parser.beginParsing(inputReader)

    schema = schemaFunc()
  }

  override def close(): Unit = {
    if (parser != null) {
      parser.stopParsing()
    }
    if (inputReader != null) {
      inputReader.close()
    }
  }

  override def serializeState(
      currentIteratorState: Iterator[(TupleLike, Option[PortIdentity])],
      checkpoint: CheckpointState
  ): Iterator[(TupleLike, Option[PortIdentity])] = {
    checkpoint.save(
      "numOutputRows",
      numRowGenerated
    )
    currentIteratorState
  }

  override def deserializeState(
      checkpoint: CheckpointState
  ): Iterator[(TupleLike, Option[PortIdentity])] = {
    open()
    numRowGenerated = checkpoint.load("numOutputRows")
    var tupleIterator = produceTuple().drop(numRowGenerated)
    if (limit.isDefined) tupleIterator = tupleIterator.take(limit.get - numRowGenerated)
    tupleIterator.map(tuple => (tuple, Option.empty))
  }

  override def getEstimatedCheckpointCost: Long = 0L
}

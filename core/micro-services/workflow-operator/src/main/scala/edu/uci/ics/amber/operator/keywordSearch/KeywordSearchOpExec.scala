package edu.uci.ics.amber.operator.keywordSearch

import edu.uci.ics.amber.core.tuple.Tuple
import edu.uci.ics.amber.operator.filter.FilterOpExec
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.analysis.core.SimpleAnalyzer
import org.apache.lucene.index.memory.MemoryIndex
import org.apache.lucene.search.Query

class KeywordSearchOpExec(attributeName: String, keyword: String) extends FilterOpExec {
  @transient private lazy val analyzer = new SimpleAnalyzer()
  @transient lazy val query: Query = new QueryParser(attributeName, analyzer).parse(keyword)
  @transient private lazy val memoryIndex: MemoryIndex = new MemoryIndex()

  this.setFilterFunc(findKeyword)
  private def findKeyword(tuple: Tuple): Boolean = {
    Option[Any](tuple.getField(attributeName)).map(_.toString).exists { fieldValue =>
      memoryIndex.addField(attributeName, fieldValue, analyzer)
      val isMatch = memoryIndex.search(query) > 0.0f
      memoryIndex.reset()
      isMatch
    }
  }

}

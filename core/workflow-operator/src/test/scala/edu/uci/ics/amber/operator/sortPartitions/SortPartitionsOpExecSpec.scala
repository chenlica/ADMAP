package edu.uci.ics.amber.operator.sortPartitions

import edu.uci.ics.amber.core.tuple._
import edu.uci.ics.amber.util.JSONUtils.objectMapper
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
class SortPartitionsOpExecSpec extends AnyFlatSpec with BeforeAndAfter {
  val tupleSchema: Schema = Schema()
    .add(new Attribute("field1", AttributeType.STRING))
    .add(new Attribute("field2", AttributeType.INTEGER))
    .add(new Attribute("field3", AttributeType.BOOLEAN))

  val tuple: Int => Tuple = i =>
    Tuple
      .builder(tupleSchema)
      .add(new Attribute("field1", AttributeType.STRING), "hello")
      .add(new Attribute("field2", AttributeType.INTEGER), i)
      .add(
        new Attribute("field3", AttributeType.BOOLEAN),
        true
      )
      .build()

  val opDesc: SortPartitionsOpDesc = new SortPartitionsOpDesc()
  opDesc.sortAttributeName = "field2"
  var opExec: SortPartitionsOpExec = _
  before {
    opExec = new SortPartitionsOpExec(objectMapper.writeValueAsString(opDesc))
  }

  it should "open" in {

    opExec.open()

  }

  it should "output in order" in {

    opExec.open()
    opExec.processTuple(tuple(3), 0)
    opExec.processTuple(tuple(1), 0)
    opExec.processTuple(tuple(2), 0)
    opExec.processTuple(tuple(5), 0)

    val outputTuples: List[Tuple] =
      opExec
        .onFinish(0)
        .map(tupleLike => tupleLike.asInstanceOf[SchemaEnforceable].enforceSchema(tupleSchema))
        .toList
    assert(outputTuples.size == 4)
    assert(outputTuples(0).equals(tuple(1)))
    assert(outputTuples(1).equals(tuple(2)))
    assert(outputTuples(2).equals(tuple(3)))
    assert(outputTuples(3).equals(tuple(5)))
    opExec.close()
  }

}

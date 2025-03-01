package edu.uci.ics.amber.engine.architecture.sendsemantics.partitioners

import edu.uci.ics.amber.core.tuple.Tuple
import edu.uci.ics.amber.engine.architecture.sendsemantics.partitionings.RoundRobinPartitioning
import edu.uci.ics.amber.core.virtualidentity.ActorVirtualIdentity

case class RoundRobinPartitioner(partitioning: RoundRobinPartitioning) extends Partitioner {
  private var roundRobinIndex = 0
  private val receivers = partitioning.channels.map(_.toWorkerId).distinct

  override def getBucketIndex(tuple: Tuple): Iterator[Int] = {
    roundRobinIndex = (roundRobinIndex + 1) % receivers.length
    Iterator(roundRobinIndex)
  }

  override def allReceivers: Seq[ActorVirtualIdentity] = receivers
}

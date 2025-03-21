package edu.uci.ics.texera.web

import edu.uci.ics.amber.engine.common.AmberRuntime
import org.apache.commons.jcs3.access.exception.InvalidArgumentException

import scala.annotation.tailrec

object ComputingUnitWorker {

  type OptionMap = Map[Symbol, Any]

  def parseArgs(args: Array[String]): OptionMap = {
    @tailrec
    def nextOption(map: OptionMap, list: List[String]): OptionMap = {
      list match {
        case Nil => map
        case "--serverAddr" :: value :: tail =>
          nextOption(map ++ Map(Symbol("serverAddr") -> value), tail)
        case option :: tail =>
          throw new InvalidArgumentException("unknown command-line arg")
      }
    }

    nextOption(Map(), args.toList)
  }

  def main(args: Array[String]): Unit = {
    val argMap = parseArgs(args)

    // start actor system worker node
    AmberRuntime.startActorWorker(argMap.get(Symbol("serverAddr")).asInstanceOf[Option[String]])
  }

}

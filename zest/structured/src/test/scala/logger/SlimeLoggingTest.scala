package logger

import com.slime.LazyLogging
import logger.element.{Mi, MyLogInfo}
import utils.UTTrait

import scala.collection.mutable

class SlimeLoggingTest extends UTTrait with LazyLogging {
//  "Slime" should "log structured information" in {
//    val logInfo = MyLogInfo("string value a", 2, 3.3, List("str 1", "str 2", "str 3"))
//    val l       = Mi("a", 2.22)
//
//
//    logger.info("logging message", "log_info" -> logInfo, "log_i"    -> l)
//    logger.info("logging message", "log_i"    -> l)
//  }

  it should "log map" in {
    val map = Map("a" -> 18, "b" -> "22", "c" -> 3.3)
//    val map = List(1,2,3)
    logger.info("log msg", "map"-> map)

    val singleMap = Map("a" -> 12, "b" -> 13)
    logger.info("log msg", "simple map" -> singleMap)
  }

//  "Slime" should "log exception" in {
//    try {
//      funcThrowException()
//    } catch {
//      case e: Exception => logger.error("got exception", "exception" -> e)
//    }
//  }

  def funcThrowException(): Unit =
    throw new Exception("my exception messages")
}

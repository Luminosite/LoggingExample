package logger

import com.slime.LazyLogging
import logger.element.{ Mi, MyLogInfo }
import utils.UTTrait

class SlimeLoggingTest extends UTTrait with LazyLogging {
  "Slime" should "log structured information" in {
    val logInfo = MyLogInfo("string value a", 2, 3.3, List("str 1", "str 2", "str 3"))
    val l       = Mi("a", 2.22)


    logger.info("logging message", "log_info" -> logInfo, "log_i"    -> l)
    logger.info("logging message", "log_i"    -> l)
  }

  "Slime" should "log exception" in {
    try {
      funcThrowException()
    } catch {
      case e: Exception => logger.error("got exception", "exception" -> e)
    }
  }

  def funcThrowException(): Unit =
    throw new Exception("my exception messages")
}

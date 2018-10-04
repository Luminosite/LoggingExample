package priv.l.logging.test.metrics
import com.slime.LazyLogging
import priv.l.logging.test.metrics.element.MyLogInfo
import priv.l.logging.test.util.UTTrait

class SlimeElasticLogTest extends UTTrait with LazyLogging {
  "Slime" should "log structured infomation" in {
    val logInfo = MyLogInfo("string value a", 2, 3.3, List("str 1", "str 2", "str 3"))
    logger.info("logging message", "log_info" -> logInfo)
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

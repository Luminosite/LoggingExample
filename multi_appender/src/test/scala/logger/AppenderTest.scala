package logger

import org.slf4j.LoggerFactory
import utils.UTTrait
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

class AppenderTest extends UTTrait {
  "MultiAppender" should "append log in different ways" in {
    val logger = LoggerFactory.getLogger("Test")
    logger.info("test")
    logger.getClass.getName shouldBe "ch.qos.logback.classic.Logger"
  }
}

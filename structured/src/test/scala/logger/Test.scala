package logger

import org.slf4j.LoggerFactory
import utils.UTTrait

class Test extends UTTrait {

  "Log Factory" should "get right logger" in {
    val logger = LoggerFactory.getLogger("Test")
    logger.getClass.getName shouldBe "ch.qos.logback.classic.Logger"
  }
}

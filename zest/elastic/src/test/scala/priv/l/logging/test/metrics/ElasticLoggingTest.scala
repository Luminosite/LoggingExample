package priv.l.logging.test.metrics

import org.slf4j.LoggerFactory
import priv.l.logging.test.util.UTTrait

class ElasticLoggingTest extends UTTrait {
  "logger" should "log info to elastic" in {
    val logger = LoggerFactory.getLogger(this.getClass.getName)
    logger.info("my message to log")
  }
}

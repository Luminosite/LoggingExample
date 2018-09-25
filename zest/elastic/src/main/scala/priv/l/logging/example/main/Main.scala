package priv.l.logging.example.main
import org.slf4j.LoggerFactory

object Main extends App {
  val logger = LoggerFactory.getLogger(this.getClass)

  logger.info("kmsg: my info test")
}

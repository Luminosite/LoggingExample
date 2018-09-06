package priv.l.logging.example.main
import com.typesafe.scalalogging.Logger

object ScalaLogMain extends App {
  val logger: Logger = Logger(this.getClass.getName)
  logger.info("kmsg: first log")
  Thread.sleep(1000)
  logger.trace("kmsg: trace")
  Thread.sleep(1000)
  logger.warn("kmsg: warn")
  Thread.sleep(1000)
  logger.error("kmsg: error")
  Thread.sleep(1000)
  logger.debug("kmsg: debug")
}

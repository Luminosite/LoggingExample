package priv.l.logging.example.foo
import com.typesafe.scalalogging.Logger

class Bar {
  def doIt(): Unit = {
    val logger = Logger(this.getClass.getName)
    logger.info("kmsg: first log")
    logger.debug("kmsg: debug")
  }

}

package priv.l.logging.example.main

import com.typesafe.scalalogging.Logger

object Main extends App {

  val logger: Logger = Logger(this.getClass.getName)
  logger.info("kmsg: info 1")
  logger.info("info 2")
  logger.info("kmsg: info 3")
  logger.info("info 4")
  logger.info("kmsg: info 5")
  logger.info("kmsg: info 6")

}

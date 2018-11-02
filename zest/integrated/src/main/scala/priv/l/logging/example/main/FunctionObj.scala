package priv.l.logging.example.main
import priv.l.logging.example.logger.LazyLogging

object FunctionObj extends LazyLogging {
  def operation(): Unit =
    info("logs from Function Object")
}

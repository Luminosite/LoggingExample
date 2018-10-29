package priv.l.logging.example.main
import priv.l.logging.example.logger.LazyLogging

object Main extends App with LazyLogging {

  {
    info("log info level message")
    error("log error level message")
    info("KV values", "freeKey4"          -> "value_4", "freeKey5" -> "value_5", "freeKey6" -> 9.9)
    error("log error level message", "k1" -> 1, "k2"               -> "v2", "k3"            -> 3.3)
  }

  logMetrics()
  logExceptions()

  def logMetrics(): Unit = {
    val metrics = Map(
      "v1" -> 33,
      "v2" -> "value"
    )
    logMetrics(metrics)

    val metrics2 = Map(
      "v21" -> 99,
      "v22" -> "value2"
    )
    logMetrics(metrics2)
  }

  def logExceptions(): Unit =
    try {
      funcThrowException()
    } catch {
      case e: Exception =>
        error("get error message", e)
        error("get error message", e, "key1" -> e.hashCode(), "key2" -> e.getMessage)
    }

  def funcThrowException(): Unit = {
    val root   = new Exception("root exception")
    val middle = new Exception("middle exception", root)
    throw new Exception("this is test exception's message", middle)
  }
}

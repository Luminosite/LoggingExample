package priv.l.logging.example.main

import com.slime.LazyLogging
import priv.l.logging.example.logger.StructuredLogger
import priv.l.logging.example.logger.struct.LogStruct

object Main extends App with LazyLogging {

  {
    StructuredLogger.logKVs("freeKey1" -> 1, "freeKey2" -> "value_2", "freeKey3" -> 3.3)
    StructuredLogger.debugLog("log info level message")
    StructuredLogger.errorLog("log error level message")
    StructuredLogger.logKVs("freeKey4" -> "value_4", "freeKey5" -> "value_5", "freeKey6" -> 9.9)
  }

  logMetrics()
  logMyStructs()
  logExceptions()

  def logMetrics(): Unit = {
    val metrics = Map(
      "v1" -> 33,
      "v2" -> "value"
    )
    StructuredLogger.logMetrics(metrics)

    val metrics2 = Map(
      "v21" -> 99,
      "v22" -> "value2"
    )
    StructuredLogger.logMetrics(metrics2)
  }

  def logMyStructs(): Unit = {
    val m1 = LogStruct("string a", 2, 3.3, List("l1", "l2", "l3"))
    StructuredLogger.logStructs(m1)
  }

  def logExceptions(): Unit =
    try {
      funcThrowException()
    } catch {
      case e: Exception => StructuredLogger.logException(e)
    }

  def funcThrowException(): Unit = {
    val root   = new Exception("root exception")
    val middle = new Exception("middle exception", root)
    throw new Exception("this is test exception's message", middle)
  }
}

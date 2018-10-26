package priv.l.logging.example.logger

import com.slime.{ LazyLogging, Logger }
import priv.l.logging.example.logger.struct.LogStruct

object StructuredLogger extends LazyLogging {
  private val metricsLogger = Logger("bsi_metrics")
  private val messageLogger = Logger("bsi_message")

  def logException(e: Exception): Unit = {
    val stack = getTraces(e)
    val eInfo = ExceptionInfo(e.getMessage, stack)
    messageLogger.error("exception_info", "exception" -> eInfo)
  }

  private[this] def getTraces(t: Throwable): List[List[String]] = {
    val current = t.toString :: t.getStackTrace.map(_.toString).toList
    val pre     = Option(t.getCause).map(getTraces).getOrElse(List())
    current :: pre
  }

  def logKVs(kvs: (String, Any)*): Unit = {
    val map: Map[String, Any] = kvs.toMap
    metricsLogger.info("key_value_pairs", "key_value_pairs" -> map)
  }

  def debugLog(log: String, level: String = "info"): Unit =
    messageLogger.info("message", "message_body" -> log)

  def errorLog(log: String): Unit =
    messageLogger.error("message", "message_body" -> log)

  def logMetrics(map: Map[String, Any]): Unit =
    metricsLogger.info("metrics", "metrics" -> map)

  def logStructs(struct: LogStruct): Unit = {
    val structName = struct.getClass.getName
    metricsLogger.info("struct", "struct_name" -> structName, "struct" -> struct)
  }
}

package priv.l.logging.helper
import com.slime.LazyLogging

case class ExceptionInfo(msg: String, stack: List[List[String]])

object DefaultLogger extends LazyLogging {
  def logException(e: Exception): Unit = {
    val stack = getTraces(e)
    val eInfo = ExceptionInfo(e.getMessage, stack)
    logger.error("exception_info", "exception" -> eInfo)
  }

  def getTraces(t: Throwable): List[List[String]] = {
    val current = t.toString :: t.getStackTrace.map(_.toString).toList
    val pre     = Option(t.getCause).map(getTraces).getOrElse(List())
    current :: pre
  }
}

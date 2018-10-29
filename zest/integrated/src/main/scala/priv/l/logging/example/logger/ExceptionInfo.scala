package priv.l.logging.example.logger

final case class ExceptionInfo(msg: String, stack: List[List[String]])

object ExceptionInfo {
  def apply(ex: Exception): ExceptionInfo = new ExceptionInfo(ex.getMessage, getTraces(ex))

  private def getTraces(t: Throwable): List[List[String]] = {
    val current = t.toString :: t.getStackTrace.map(_.toString).toList
    val pre     = Option(t.getCause).map(t => getTraces(t)).getOrElse(List())
    current :: pre
  }
}

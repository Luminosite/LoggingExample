package priv.l.logging.helper

import com.slime.LazyLogging

import scala.util.control.NonFatal
import scala.util.{ Failure, Success, Try }

object TryLog extends LazyLogging {
  def apply[A](computable: => A): Try[A] =
    try Success(computable)
    catch {
      case NonFatal(e) =>
        logger.error("got exception", "exception" -> e)
        new Exception()
        Failure(e)
    }
}

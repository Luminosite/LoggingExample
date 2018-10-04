package logger
import priv.l.logging.helper.TryLog
import utils.UTTrait

import scala.util.{ Failure, Success }

class ExceptionLogTest extends UTTrait {
  "TryLog" should "log exception automatically" in {
    val t = TryLog {
      funcThrowException()
    }

    t match {
      case Success(_) => println("success")
      case Failure(_) => println("failure")
    }
  }

  def funcThrowException(): Unit =
    throw new Exception("this is test exception's message")
}

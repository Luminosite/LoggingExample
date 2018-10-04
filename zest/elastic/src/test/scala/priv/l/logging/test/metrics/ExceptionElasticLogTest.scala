package priv.l.logging.test.metrics

import priv.l.logging.helper.TryLog
import priv.l.logging.test.util.UTTrait

import scala.util.{ Failure, Success }

class ExceptionElasticLogTest extends UTTrait {
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

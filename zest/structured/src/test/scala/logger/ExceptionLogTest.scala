package logger
import priv.l.logging.helper.{ DefaultLogger, TryLog }
import utils.UTTrait

import scala.util.{ Failure, Success }

class ExceptionLogTest extends UTTrait {
//  "TryLog" should "log exception automatically" in {
//    val t = TryLog {
//      funcThrowException()
//    }
//
//    t match {
//      case Success(_) => println("success")
//      case Failure(_) => println("failure")
//    }
//  }

  def funcThrowException(): Unit = {
    val root   = new Exception("root exception")
    val middle = new Exception("middle exception", root)
    throw new Exception("this is test exception's message", middle)
  }

  it should "get Exception Stack" in {
    try {
      funcThrowException()
    } catch {
      case e: Exception =>
        DefaultLogger.logException(e)
        e.printStackTrace()
    }
  }

}

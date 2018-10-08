package priv.l.logging.example.main

import com.codahale.metrics.{ MetricRegistry, Slf4jReporter }
import com.slime.LazyLogging
import org.slf4j.LoggerFactory
import priv.l.logging.example.struct.{ MyStruct1, MyStruct2 }

object Main extends App with LazyLogging {

  logMyStructs()
  logExceptions()
  metricsLog()

  def logMyStructs(): Unit = {
    val m1 = MyStruct1("string a", 2, 3.3, List("l1", "l2", "l3"))
    logger.info("log my info in to elastic test", "e" -> m1)

    val m2 = MyStruct2(99, "struct 2 tp", 0.98, List(1, 2, 3, 4, 5))
    logger.info("log my info2 in to elastic test", "e" -> m2)
  }

  def logExceptions(): Unit = {
    val exception = new Exception("exception message in exception")
    logger.error("error message in log", "exception" -> exception)
  }

  def metricsLog(): Unit = {
    val metrics = new MetricRegistry()
    val meter   = metrics.meter("test_meter")

    println("report in the end")
    val reporter = Slf4jReporter
      .forRegistry(metrics)
      .outputTo(LoggerFactory.getLogger("com.example.metrics"))
      .build

    (1 to 100).foreach { i =>
      val t = new Thread() {
        override def run(): Unit = {
          Thread.sleep(i * 20)
          meter.mark()
        }
      }
      t.start()
    }

    Thread.sleep(3000)

    reporter.report()
    reporter.close()
  }
}

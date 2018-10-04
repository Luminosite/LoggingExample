package logger
import com.codahale.metrics.MetricRegistry
import utils.UTTrait
import com.codahale.metrics.Slf4jReporter
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

class MetricsTest extends UTTrait {
  "Metric" should "report in the end" in {
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

    meter.getCount shouldBe 100
    reporter.report()
    reporter.close()
  }

  "Metric" should "report every second" in {
    val metrics = new MetricRegistry()
    val meter   = metrics.meter("test_meter")

    println("report every second")
    val reporter = Slf4jReporter
      .forRegistry(metrics)
      .outputTo(LoggerFactory.getLogger("com.example.metrics"))
      .convertRatesTo(TimeUnit.SECONDS)
      .convertDurationsTo(TimeUnit.SECONDS)
      .build
    reporter.start(1, TimeUnit.SECONDS)

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

    meter.getCount shouldBe 100
    reporter.close()
  }
}

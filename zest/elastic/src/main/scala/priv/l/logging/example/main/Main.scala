package priv.l.logging.example.main
import com.slime.LazyLogging
import org.slf4j.LoggerFactory

object Main extends App with LazyLogging {
  val m = MyLog("string a", 2, 3.3, List("l1", "l2", "l3"))
  logger.info("log my info in to elastic test", "e" -> m)
}

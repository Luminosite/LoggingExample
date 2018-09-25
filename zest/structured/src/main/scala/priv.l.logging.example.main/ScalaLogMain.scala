package priv.l.logging.example.main
import com.slime.LazyLogging

final case class E(a: String, b: Int, c: Double, d: List[String])

object ScalaLogMain extends App with LazyLogging {
  logger.error("error: message")
  val e: E = E("a", 1, 0.37, List("l1", "l2", "l3"))
  logger.info("kmsg: e: ", "e" -> e)
}

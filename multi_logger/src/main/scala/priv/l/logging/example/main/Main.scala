package priv.l.logging.example.main

import com.typesafe.scalalogging.Logger
import priv.l.logging.example.foo.{ Bar, Foo }

object Main extends App {
  val logger: Logger = Logger(this.getClass.getName)
  logger.info("kmsg: info")
  logger.debug("kmsg: debug")

  val foo: Foo = new Foo()
  foo.doIt()

  val bar: Bar = new Bar()
  bar.doIt()
}

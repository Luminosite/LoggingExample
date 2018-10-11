package priv.l.logging.exception.sentry

import io.sentry.Sentry
import io.sentry.event.{BreadcrumbBuilder, UserBuilder}
import priv.l.logging.test.utils.UTTrait

class SentryTest extends UTTrait{
  "Sentry" should "send exception info" in {
    logWithStaticAPI()
  }

  def logWithStaticAPI(): Unit ={
    Sentry.getContext.recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build)

    // Set the user in the current context.
    Sentry.getContext.setUser(new UserBuilder().setEmail("luminosite@sentry.io").build)

    // Add extra data to future events in this context.
    Sentry.getContext.addExtra("extra", "thing")

    // Add an additional tag to future events in this context.
    Sentry.getContext.addTag("tagName", "tagValue")

    /*
         This sends a simple event to Sentry using the statically stored instance
         that was created in the ``main`` method.
         */
    Sentry.capture("This is a test")

    try unsafeMethod()
    catch {
      case e: Exception =>
        Sentry.capture(e)
      e.printStackTrace()
    }
  }

  def unsafeMethod(): Unit = {
    throw new UnsupportedOperationException("You shouldn't call this!")
  }
}

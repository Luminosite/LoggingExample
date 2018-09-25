package priv.l.logging.example.filter
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

class SampleFilter extends Filter[ILoggingEvent] {
  override def decide(event: ILoggingEvent): FilterReply =
    if (event.getMessage.contains("kmsg")) {
      FilterReply.ACCEPT
    } else {
      FilterReply.NEUTRAL
    }
}

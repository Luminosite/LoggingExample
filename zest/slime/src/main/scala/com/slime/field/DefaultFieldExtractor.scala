package com.slime.field

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneOffset}
import java.util.Date

import ch.qos.logback.classic.spi.LoggingEvent
import ch.qos.logback.core.CoreConstants
import com.slime._

import scala.collection.JavaConverters._

class DefaultFieldExtractor extends FieldExtractor {

  override def extract(field: String, event: LoggingEvent): Option[Value] =
    DefaultFieldExtractor.fieldExtractors.get(field).map(_.apply(event))

}

object DefaultFieldExtractor {

  val tsFormat = new SimpleDateFormat(CoreConstants.ISO8601_PATTERN)

  private val dateFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME.withZone(ZoneOffset.UTC)
  private val ISODateFormat = new SimpleDateFormat(CoreConstants.ISO8601_PATTERN)
  //    val ts = event.getTimeStamp
  //    val tsStr = getTimeStr(ts)
  //    val threadName = event.getThreadName
  //    val loggerName = event.getLoggerName
  //    val additional = Seq(
  //      ("logger", StringValue(loggerName)),
  //      ("date", StringValue(tsStr)),
  //      ("thread", StringValue(threadName))
  //    )

  val fieldExtractors: Map[String, LoggingEvent => Value] =
    Map(
      "level" -> (e => StringValue(e.getLevel.toString)),
//      "message" -> (e => StringValue(e.getFormattedMessage)),
      "message" -> (e => StringValue(e.getMessage)),
      "thread" -> (e => StringValue(e.getThreadName)),
      "logger" -> (e => StringValue(e.getLoggerName)),
      "mdc" -> (e => NestedValue(e.getMDCPropertyMap.asScala.mapValues(StringValue).toSeq)),
      "timestamp" -> (e => NumberValue(e.getTimeStamp)),
      "ts" -> (e => StringValue(dateFormatter.format(Instant.ofEpochMilli(e.getTimeStamp)))),
      "date" -> (e => StringValue(ISODateFormat.format(new Date(new Timestamp(e.getTimeStamp).getTime)))),
      "caller" -> (e => StringValue(caller(e.getCallerData)))
    )

  // The first entry comes from the logger library, being the 2nd element the actual caller
  private def caller(stack: Array[StackTraceElement]): String = stack match {
    case Array(_, c, _ *) => s"(${c.getFileName}:${c.getLineNumber})"
    case _ => "unknown"
  }

}

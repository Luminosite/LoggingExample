package com.slime

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import ch.qos.logback.classic.spi.LoggingEvent
import ch.qos.logback.core.CoreConstants
import ch.qos.logback.core.encoder.EncoderBase
import com.slime.field.{DefaultFieldExtractor, FieldExtractor}
import com.slime.format.Text
import java.lang.reflect.Field
import java.nio.charset.StandardCharsets

class Encoder extends EncoderBase[LoggingEvent] {

  private val debug = false

  var format: Format = new Text

//  Map(
//    "level" -> (e => StringValue(e.getLevel.toString)),
//    "message" -> (e => StringValue(e.getFormattedMessage)),
//    "thread" -> (e => StringValue(e.getThreadName)),
//    "logger" -> (e => StringValue(e.getLoggerName)),
//    "mdc" -> (e => NestedValue(e.getMDCPropertyMap.asScala.mapValues(StringValue).toSeq)),
//    "timestamp" -> (e => NumberValue(e.getTimeStamp)),
//    "ts" -> (e => StringValue(dateFormatter.format(Instant.ofEpochMilli(e.getTimeStamp)))),
//    "caller" -> (e => StringValue(caller(e.getCallerData)))
//  )

  var fields: Seq[String] = Seq("ts", "thread", "logger", "level", "message")

  var fieldExtractor: FieldExtractor = new DefaultFieldExtractor

  override def encode(event: LoggingEvent): Array[Byte] = {
    doDebug(s"encode $event")

    // additional attributes
//    val ts = event.getTimeStamp
//    val tsStr = getTimeStr(ts)
//    val threadName = event.getThreadName
//    val loggerName = event.getLoggerName
//    val additional = Seq(
//      ("logger", StringValue(loggerName)),
//      ("date", StringValue(tsStr)),
//      ("thread", StringValue(threadName))
//    )

    val values = event.getMarker match {
      case mm: AnnotationMarker =>
        doDebug(s"marker ${mm.annotations}")
        mm.encoded
      case _ =>
        doDebug("unknown marker")
        Seq.empty
    }


    val b = format(event, values)
    val str = new String(b, StandardCharsets.UTF_8).replace("\n", "")
    val clazz = classOf[LoggingEvent]
    val formatVal = clazz.getDeclaredField("formattedMessage")
    formatVal.setAccessible(true)
    formatVal.set(event, str)
    b
  }

  def format(event: LoggingEvent, values: Seq[(String, Value)]): Array[Byte] = {
    format.format(extractFields(event), values)
  }

  def extractFields(event: LoggingEvent): Seq[(String, Value)] = {
    fields.flatMap(f => fieldExtractor.extract(f, event).map(f -> _))
  }

  override def headerBytes(): Array[Byte] = {
    Array.emptyByteArray
  }

  override def footerBytes(): Array[Byte] = {
    Array.emptyByteArray
  }

  def setFormat(format: Format): Unit = {
    doDebug(s"setFormat ${format.getClass}")
    this.format = format
  }

  def setFields(allFields: String): Unit = {
    doDebug(s"setFields $allFields")
    this.fields = allFields.split(",").map(_.toLowerCase.trim)
  }

  def setFieldExtractor(fieldExtractor: FieldExtractor): Unit = {
    this.fieldExtractor = fieldExtractor
  }

  @inline
  private def doDebug(msg: => String): Unit = {
    if (debug) addInfo(msg)
  }

}

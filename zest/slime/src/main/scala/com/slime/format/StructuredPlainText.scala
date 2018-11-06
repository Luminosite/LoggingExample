package com.slime.format

import java.nio.charset.StandardCharsets

import com.slime._

class StructuredPlainText extends Format {

  override def format(event: Seq[(String, Value)], data: Seq[(String, Value)]): Array[Byte] = {
    val builder = StringBuilder.newBuilder
    appendEvent(event, builder)

    val structuredData = data.filter(t => t._1 != "message" && t._1 != "exception")
    if(structuredData.nonEmpty){
      val structuredLog = formatValue(NestedValue(structuredData), tab="")(StringBuilder.newBuilder).toString()
      builder.append(structuredLog)
      builder.append("\n")
    }

    appendException(data, builder)

    builder.toString().getBytes(StandardCharsets.UTF_8)
  }

  private def appendEvent(event: Seq[(String, Value)],
                          builder: StringBuilder): Unit = {
    val logInfoTags = List("ts", "level", "logger", "thread")
    val logEvent = logInfoTags.map(tag => event.find(_._1 == tag).get)
    val logHeader = formatEventValue(logEvent).mkString("", " ", ": ")
    val message = event
      .find(_._1 == "message")
      .map[String](tup =>
        tup._2 match {
          case StringValue(str) => str
          case v                => throw new IllegalArgumentException(s"unsupported structure for message in log: $v")
      })
      .getOrElse("")
    builder.append(logHeader)
    builder.append(message)
    builder.append("\n")
  }

  def appendStacks(stack: Value, builder: StringBuilder, header: String = ""): Unit = {
    builder.append(header)
    stack match {
      case SeqValue(values) =>
        values.head match {
          case StringValue(str) =>
            builder.append(str)
            builder.append("\n")
        }
        values.tail.foreach{
          case StringValue(str) =>
            builder.append("\t")
            builder.append(str)
            builder.append("\n")
        }
    }
  }

  def appendException(data: Seq[(String, Value)], builder: StringBuilder) : Unit = {
    data.find(_._1 == "exception").foreach{tup =>
      tup._2 match {
        case NestedValue(values) =>
          values.find(t => t._1 == "stack").foreach{stackTup =>
            stackTup._2 match {
              case SeqValue(seqs) =>
                if(seqs.nonEmpty){
//                  builder.append("Log Exception:\n")
                  appendStacks(seqs.head, builder)
                  seqs.tail.foreach {s =>
                    appendStacks(s, builder, header="Caused by: ")
                  }
                }
            }
          }
      }
    }
  }

  private def formatEventValue(event: Seq[(String, Value)]): Seq[String] = event.map(_._2) map {
    case NumberValue(num) => num.toString
    case BooleanValue(b) => b.toString
    case StringValue(str) => str
    case v => throw new IllegalArgumentException(s"unsupported structure for event in log: $v")
  }

  private def formatValue(value: Value, tab: String, newLine: Boolean = true)
                         (implicit builder: StringBuilder): StringBuilder = value match {
    case NumberValue(num) =>
      if (newLine) builder.append(tab)
      builder.append(num.toString)
    case BooleanValue(b) =>
      if (newLine) builder.append(tab)
      builder.append(b.toString)
    case StringValue(str) =>
      if (newLine) builder.append(tab)
      builder.append('"')
      formatString(str)
      builder.append('"')
    case SeqValue(values) =>
      if (newLine){
        builder.append(tab)
      }
      builder.append("[\n")
      if (values.nonEmpty) {
        formatValue(values.head, tab = "\t" + tab)
        for (next <- values.tail) {
          builder.append(",\n")
          formatValue(next, tab = "\t" + tab)
        }
      }
      builder.append("\n")
      builder.append(tab)
      builder.append(']')
    case NestedValue(values) =>
      @inline
      def appendPair(pair: (String, Value)): Unit = {
        builder.append("\t" + tab)
        builder.append('"')
        formatString(pair._1)
        builder.append("\": ")
        formatValue(pair._2, tab="\t" + tab, newLine=false)
      }

      builder.append("{\n")
      if (values.nonEmpty) {
        appendPair(values.head)
        for (next <- values.tail) {
          builder.append(",\n")
          appendPair(next)
        }
      }
      builder.append("\n")
      builder.append(tab)
      builder.append("}")
  }

  private def formatString(str: String)(implicit builder: StringBuilder): Unit = str.foreach {
    case '\b' => builder.append("\\b")
    case '\f' => builder.append("\\f")
    case '\n' => builder.append("\\n")
    case '\r' => builder.append("\\r")
    case '\t' => builder.append("\\t")
    case '"' => builder.append("\\\"")
    case '\\' => builder.append("\\\\")
    case c if Character.isISOControl(c) => builder.append('\\').append("u%04x".format(c.toInt))
    case other => builder.append(other)
  }
}

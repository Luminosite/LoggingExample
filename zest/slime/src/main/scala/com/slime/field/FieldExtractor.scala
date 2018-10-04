package com.slime.field

import ch.qos.logback.classic.spi.LoggingEvent
import com.slime.Value

trait FieldExtractor {

  def extract(field: String, event: LoggingEvent): Option[Value]

}

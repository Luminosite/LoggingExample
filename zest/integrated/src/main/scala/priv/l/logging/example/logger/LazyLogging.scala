package priv.l.logging.example.logger

trait LazyLogging extends com.slime.LazyLogging {
  private def getMapPara(map: Map[String, Any]) = "map" -> map

  private def getExMapPara(ex: Exception, map: Map[String, Any]) = {
    val exParameter = "exception" -> ExceptionInfo(ex)
    (exParameter, getMapPara(map))
  }

  def info(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.info(log, mapParameter)
    } else {
      logger.info(log)
    }
  }

  def info(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.info(log, exParameter, mapParameter)
    } else {
      logger.info(log, exParameter)
    }
  }

  def error(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.error(log, mapParameter)
    } else {
      logger.error(log)
    }
  }

  def error(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.error(log, exParameter, mapParameter)
    } else {
      logger.error(log, exParameter)
    }
  }

  def warn(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.warn(log, mapParameter)
    } else {
      logger.warn(log)
    }
  }

  def warn(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.warn(log, exParameter, mapParameter)
    } else {
      logger.warn(log, exParameter)
    }
  }

  def debug(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.debug(log, mapParameter)
    } else {
      logger.debug(log)
    }
  }

  def debug(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    if (mapParameter._2.nonEmpty) {
      logger.debug(log, exParameter, mapParameter)
    } else {
      logger.debug(log, exParameter)
    }
  }

  /**
   * Log structured metrics data. The metrics data are passed as a Map[String, Any] to the function
   * @param map metrics data in Map[String, Any] structure
   */
  def logMetrics(map: Map[String, Any]): Unit =
    logger.info("metrics", "metrics" -> map)
}

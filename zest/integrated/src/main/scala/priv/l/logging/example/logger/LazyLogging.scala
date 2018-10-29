package priv.l.logging.example.logger

trait LazyLogging extends com.slime.LazyLogging {
  private def getMapPara(map: Map[String, Any]) = "map" -> map

  private def getExMapPara(ex: Exception, map: Map[String, Any]) = {
    val exParameter = "exception" -> ExceptionInfo(ex)
    (exParameter, getMapPara(map))
  }

  def info(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    logger.info(log, mapParameter)
  }

  def info(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    logger.info(log, exParameter, mapParameter)
  }

  def error(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    logger.error(log, mapParameter)
  }

  def error(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    logger.error(log, exParameter, mapParameter)
  }

  def warn(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    logger.warn(log, mapParameter)
  }

  def warn(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    logger.warn(log, exParameter, mapParameter)
  }

  def debug(log: String, kvs: (String, Any)*): Unit = {
    val mapParameter = getMapPara(kvs.toMap)
    logger.debug(log, mapParameter)
  }

  def debug(log: String, ex: Exception, kvs: (String, Any)*): Unit = {
    val (exParameter, mapParameter) = getExMapPara(ex, kvs.toMap)
    logger.debug(log, exParameter, mapParameter)
  }

  /**
   * Log structured metrics data. The metrics data are passed as a Map[String, Any] to the function
   * @param map metrics data in Map[String, Any] structure
   */
  def logMetrics(map: Map[String, Any]): Unit =
    logger.info("metrics", "metrics" -> map)
}

package priv.l.logging.example.main;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

class LogbackMain {

    public static void main(String[] argv){
        Logger logger = LoggerFactory.getLogger("LogbackMain");
        logger.info("kmsg: log from logback main");
    }
}

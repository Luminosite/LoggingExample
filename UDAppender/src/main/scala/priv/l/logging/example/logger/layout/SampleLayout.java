package priv.l.logging.example.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleLayout extends LayoutBase<ILoggingEvent> {

    public String doLayout(ILoggingEvent event) {
        StringBuilder sbuf = new StringBuilder(128);

        long ts = event.getTimeStamp();
        Date dt = new Date(new Timestamp(ts).getTime());
        String tsStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt);

        sbuf.append(tsStr);
        sbuf.append(" ");
        // application running time
        sbuf.append(event.getTimeStamp() - event.getLoggerContextVO().getBirthTime());
        sbuf.append(" ");
        sbuf.append(event.getLevel());
        sbuf.append(" [");
        sbuf.append(event.getThreadName());
        sbuf.append("] ");
        sbuf.append(event.getLoggerName());
        sbuf.append(" - ");
        sbuf.append(event.getFormattedMessage());
        sbuf.append("\n");
        return sbuf.toString();
    }
}


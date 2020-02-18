package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TimeUtils {
    public static String getTimestamp() {
        return new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(new Date());
    }

    public static String getShortTimestamp() {
        return getTimestamp();
    }
}

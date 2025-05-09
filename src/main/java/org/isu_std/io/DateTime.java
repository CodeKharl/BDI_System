package org.isu_std.io;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    public static String localDateTimeStr(DateTimeFormatter dateTimeFormatter){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(dateTimeFormatter);
    }
}

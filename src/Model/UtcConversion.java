package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UtcConversion {

    public static LocalDateTime convertLocalToUTC(LocalDateTime dateTime) {
        ZonedDateTime zdt = dateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localutc = utcdt.toLocalDateTime();
        return localutc;
    }

    public static LocalDateTime convertUTCtoLocal(LocalDateTime dateTime) {
        ZonedDateTime zdt = dateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime utcdt = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime localdt = utcdt.toLocalDateTime();
        return localdt;
    }

    /**Takes a local date time variable and formats it to date and time of day string (AM PM).*/
    public static String dtFormat(LocalDateTime ldtToFormat){
        String formatted = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        formatted = ldtToFormat.format(formatter);
        return formatted;
    }

}

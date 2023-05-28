package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**Class to perform time conversions to UTC and back. Helps for timezone management when storing
 * and retrieving from the database.*/
public class UtcConversion {

    /**Converts local time to UTC.
     * @param dateTime the datetime in system timezone
     * @return localutc the datetime in UTC*/
    public static LocalDateTime convertLocalToUTC(LocalDateTime dateTime) {
        ZonedDateTime zdt = dateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localutc = utcdt.toLocalDateTime();
        return localutc;
    }

    /**Converts UTC to system local time.
     * @param dateTime the datetime in UTC timezone
     * @return localdt the datetime in local system time*/
    public static LocalDateTime convertUTCtoLocal(LocalDateTime dateTime) {
        ZonedDateTime zdt = dateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime utcdt = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime localdt = utcdt.toLocalDateTime();
        return localdt;
    }

    /**Takes a local date time variable and formats it to date and time of day string (AM PM).
     * @param ldtToFormat date time to format
     * @return formatted String to return*/
    public static String dtFormat(LocalDateTime ldtToFormat){
        String formatted = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        formatted = ldtToFormat.format(formatter);
        return formatted;
    }
}

package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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


}

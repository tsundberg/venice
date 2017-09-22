package se.arbetsformedlingen.venice.log.elasticsearch;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

class DateUtil {
    static LocalDateTime yesterday() {
        return LocalDateTime.now().minus(1, ChronoUnit.DAYS);
    }

    static LocalDateTime getCetDateTimeFromUtc(String key) {
        String parsableString = key.substring(0, 22);
        LocalDateTime zulu = LocalDateTime.parse(parsableString);

        ZonedDateTime zuluZoned = ZonedDateTime.of(zulu, ZoneId.of("UTC"));

        return zuluZoned.withZoneSameInstant(ZoneId.of("CET")).toLocalDateTime();
    }

}

package se.arbetsformedlingen.venice.log.elasticsearch;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    static LocalDateTime yesterday() {
        LocalDateTime yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        yesterday = yesterday.plusHours(1);

        int year = yesterday.getYear();
        int month = yesterday.getMonth().getValue();
        int day = yesterday.getDayOfMonth();
        int hour = yesterday.getHour();

        yesterday = LocalDateTime.of(year, month, day, hour, 0);

        return yesterday;
    }
}

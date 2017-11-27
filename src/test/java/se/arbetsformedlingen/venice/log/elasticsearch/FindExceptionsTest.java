package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FindExceptionsTest {

    @Test
    public void add_new_excpetions_exception() {
        FindExceptions findExceptions = new FindExceptions(null, new Application("gfr"), null);

        Map<String, Integer> exceptionPerHour = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zuluZoned = ZonedDateTime.of(now, ZoneId.of("UTC"));

        String timeStamp = zuluZoned.toString();
        findExceptions.addEvent(exceptionPerHour, timeStamp);

        assertThat(exceptionPerHour.size()).isEqualTo(1);
    }

    @Test
    public void dont_add_old_excpetions_exception() {
        FindExceptions findExceptions = new FindExceptions(null, new Application("gfr"), null);

        Map<String, Integer> exceptionPerHour = new HashMap<>();
        LocalDateTime now = LocalDateTime.now().minusDays(2);
        ZonedDateTime zuluZoned = ZonedDateTime.of(now, ZoneId.of("UTC"));

        String timeStamp = zuluZoned.toString();
        findExceptions.addEvent(exceptionPerHour, timeStamp);

        assertThat(exceptionPerHour.size()).isEqualTo(0);
    }
}

package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Test;
import se.arbetsformedlingen.venice.log.LogResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class FindExceptionsTest {

    @Test
    public void find_exceptions() {
        FindExceptions findExceptions = new FindExceptions();
        findExceptions.get();
    }

    @Test
    public void convert_to_CET() {
        LocalDateTime expected = LocalDateTime.of(2017, 3, 12, 16, 42, 16);
        String sample = "2017-03-12T15:42:16.022Z";
        FindExceptions findExceptions = new FindExceptions();

        LocalDateTime actual = findExceptions.getDate(sample);

        assertThat(actual).isEqualTo(expected);
    }
}

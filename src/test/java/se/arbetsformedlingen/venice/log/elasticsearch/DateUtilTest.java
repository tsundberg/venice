package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static se.arbetsformedlingen.venice.log.elasticsearch.DateUtil.yesterday;

public class DateUtilTest {

    @Test
    public void get_yesterday_hour() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime actual = yesterday(now);

        assertThat(actual).isEqualTo(now.minusDays(1));
    }

    @Test
    public void parse_zulu_in_summer_and_translate_to_local_time() {
        String sample = "2017-06-13T23:00:00.000Z";

        LocalDateTime actual = DateUtil.getCetDateTimeFromUtc(sample);


        assertThat(actual.getDayOfMonth()).isEqualTo(14);
        assertThat(actual.getHour()).isEqualTo(1);
    }

    @Test
    public void parse_zulu_in_winter_and_translate_to_local_time() {
        String sample = "2017-02-13T23:00:00.000Z";

        LocalDateTime actual = DateUtil.getCetDateTimeFromUtc(sample);

        assertThat(actual.getDayOfMonth()).isEqualTo(14);
        assertThat(actual.getHour()).isEqualTo(0);
    }
}

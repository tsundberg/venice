package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static se.arbetsformedlingen.venice.log.elasticsearch.DateUtil.yesterday;

public class DateUtilTest {

    @Test
    public void get_yesterday_hour() {
        LocalDateTime actual = yesterday();

        assertThat(actual.getSecond()).isEqualTo(0);
    }

}

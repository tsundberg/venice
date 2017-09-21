package se.arbetsformedlingen.venice.model;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeValueTest {

    @Test
    public void normalize_event_date_to_specific_hour() {
        LocalDateTime eventTime = LocalDateTime.parse("2017-09-21T10:32:00");
        TimeSeriesValue timeSeriesValue = new TimeSeriesValue(eventTime, 4711);

        assertThat(timeSeriesValue.getTime().getHour()).isEqualTo(10);
        assertThat(timeSeriesValue.getTime().getMinute()).isEqualTo(0);
        assertThat(timeSeriesValue.getValue()).isEqualTo(4711);
    }
}

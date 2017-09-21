package se.arbetsformedlingen.venice.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsumingSystemValueTest {
    private ConsumingSystem consumingSystem = new ConsumingSystem("not important");

    @Test
    public void accept_hours_the_last_24_hours() {
        ConsumingSystemValue actual = new ConsumingSystemValue(consumingSystem, new TimeSeriesValue(LocalDateTime.parse("2017-09-21T11:24"), 4711L));
        assertThat(actual.getCalls()).isEqualTo(4711);
    }
}

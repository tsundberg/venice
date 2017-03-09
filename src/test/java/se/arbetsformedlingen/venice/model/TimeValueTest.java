package se.arbetsformedlingen.venice.model;

import org.junit.Test;

public class TimeValueTest {

    @Test
    public void accept_hours_the_last_24_hours() {
        for (int i = 0; i < 24; i++) {
            TimeSeriesValue timeSeriesValue = new TimeSeriesValue(i, 4711);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void do_accept_hours_outside_the_last_24_hours() {
        TimeSeriesValue timeSeriesValue = new TimeSeriesValue(24, 4711);
    }

    @Test(expected = IllegalArgumentException.class)
    public void do_accept_hours_before_now() {
        TimeSeriesValue timeSeriesValue = new TimeSeriesValue(-1, 4711);
    }
}

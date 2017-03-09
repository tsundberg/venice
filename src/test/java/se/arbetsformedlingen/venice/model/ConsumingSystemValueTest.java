package se.arbetsformedlingen.venice.model;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.ConsumingSystem;

public class ConsumingSystemValueTest {
    private ConsumingSystem consumingSystem = new ConsumingSystem("not important");

    @Test
    public void accept_hours_the_last_24_hours() {
        for (int i = 0; i < 24; i++) {
            new ConsumingSystemValue(consumingSystem, i, 4711L);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void do_accept_hours_outside_the_last_24_hours() {
        new ConsumingSystemValue(consumingSystem, 24, 4711L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void do_accept_hours_before_now() {
        new ConsumingSystemValue(consumingSystem, -1, 4711L);
    }
}

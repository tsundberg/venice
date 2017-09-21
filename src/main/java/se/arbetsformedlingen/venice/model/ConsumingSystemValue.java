package se.arbetsformedlingen.venice.model;

import java.time.LocalDateTime;

public class ConsumingSystemValue implements Comparable<ConsumingSystemValue> {
    private ConsumingSystem consumingSystem;
    private TimeSeriesValue value;

    public ConsumingSystemValue(ConsumingSystem consumingSystem, TimeSeriesValue value) {
        this.consumingSystem = consumingSystem;
        this.value = value;
    }

    public ConsumingSystem getConsumingSystem() {
        return consumingSystem;
    }

    public LocalDateTime getTime() {
        return value.getTime();
    }

    public Long getCalls() {
        return value.getValue();
    }

    @Override
    public int compareTo(ConsumingSystemValue o) {
        int cmp = value.compareTo(o.value);
        if (cmp != 0) {
            return cmp;
        }

        return consumingSystem.getName().compareTo(o.consumingSystem.getName());
    }
}

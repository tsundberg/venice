package se.arbetsformedlingen.venice.model;

public class ConsumingSystemValue implements Comparable<ConsumingSystemValue> {
    private ConsumingSystem consumingSystem;
    private Integer time;
    private Long calls;

    public ConsumingSystemValue(ConsumingSystem consumingSystem, Integer time, Long calls) {
        if (time < 0 || time > 23) {
            throw new IllegalArgumentException("Time values are only allowed the last 24 hours, i.e. 0 -- 23");
        }

        this.consumingSystem = consumingSystem;
        this.time = time;
        this.calls = calls;
    }

    public ConsumingSystem getConsumingSystem() {
        return consumingSystem;
    }

    public Integer getTime() {
        return time;
    }

    public Long getCalls() {
        return calls;
    }

    @Override
    public int compareTo(ConsumingSystemValue o) {
        int cmp = time.compareTo(o.time);
        if (cmp != 0) {
            return cmp;
        }

        return consumingSystem.getName().compareTo(o.consumingSystem.getName());
    }
}

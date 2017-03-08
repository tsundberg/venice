package se.arbetsformedlingen.venice.model;

import java.util.Objects;

/**
 * A limited time series, it will not allow times outside the last 24 hours.
 * I.e. 0 -- 23
 */
public class TimeValue implements Comparable<TimeValue> {
    private Integer time;
    private int value;

    public TimeValue(int time, int value) {
        if (time < 0 || time > 23) {
            throw new IllegalArgumentException("Time values are only allowed the last 24 hours, i.e. 0 -- 23");
        }

        this.time = time;
        this.value = value;
    }

    public int getTime() {
        return time;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeValue timeValue = (TimeValue) o;
        return value == timeValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

    @Override
    public int compareTo(TimeValue o) {
        return time.compareTo(o.time);
    }

    @Override
    public String toString() {
        return time + ":" + value;
    }
}

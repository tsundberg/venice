package se.arbetsformedlingen.venice.model;

import java.util.Objects;

/**
 * A limited time series, it will not allow times outside the last 24 hours.
 * I.e. 0 -- 23
 */
public class TimeSeriesValue implements Comparable<TimeSeriesValue> {
    private Integer time;
    private int value;

    public TimeSeriesValue(int time, int value) {
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
        TimeSeriesValue timeSeriesValue = (TimeSeriesValue) o;
        return value == timeSeriesValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

    @Override
    public int compareTo(TimeSeriesValue o) {
        return time.compareTo(o.time);
    }

    @Override
    public String toString() {
        return time + ":" + value;
    }
}

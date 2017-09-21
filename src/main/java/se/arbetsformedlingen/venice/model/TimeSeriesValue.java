package se.arbetsformedlingen.venice.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class TimeSeriesValue implements Comparable<TimeSeriesValue> {
    private LocalDateTime time;
    private long value;

    public TimeSeriesValue(LocalDateTime time, long value) {
        this.time = normalized(time);
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeriesValue timeSeriesValue = (TimeSeriesValue) o;
        return time.equals(timeSeriesValue.time);
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

    private LocalDateTime normalized(LocalDateTime time) {
        int year = time.getYear();
        int month = time.getMonth().getValue();
        int day = time.getDayOfMonth();
        int hour = time.getHour();

        return LocalDateTime.of(year, month, day, hour, 0);
    }
}

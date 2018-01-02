package se.arbetsformedlingen.venice.log;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CallsPerHour
{
    private LocalDate date;
    private int hour;
    private int calls;

    public CallsPerHour(LocalDate date, int hour, int calls) {
        this.date = date;
        this.hour = hour;
        this.calls = calls;
    }

    public LocalDate getDate() { return date; }
    public int getHour() { return hour; }
    public int getCalls() { return calls; }

    public LocalDateTime getDateTime() {
        return date.atTime(hour, 0);
    }
}

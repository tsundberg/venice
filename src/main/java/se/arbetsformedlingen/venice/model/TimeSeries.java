package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeSeries {
    private List<TimeSeriesValue> series = new ArrayList<>();

    public TimeSeries(TimeSeriesValue... timeSeriesValues) {
        Collections.addAll(series, timeSeriesValues);
        Collections.sort(series);
    }

    public List<TimeSeriesValue> getTimeValues() {
        return series;
    }
}

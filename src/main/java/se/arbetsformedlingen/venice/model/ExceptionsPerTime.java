package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExceptionsPerTime {
    private Application application;
    private List<TimeSeriesValue> series = new ArrayList<>();

    public ExceptionsPerTime(Application application, TimeSeriesValue... timeSeriesValues) {
        this.application = application;
        Collections.addAll(series, timeSeriesValues);
        Collections.sort(series);
    }

    public Application getApplication() {
        return application;
    }

    public List<TimeSeriesValue> getTimeValues() {
        return series;
    }
}

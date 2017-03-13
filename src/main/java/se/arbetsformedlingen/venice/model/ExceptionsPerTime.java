package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExceptionsPerTime {
    private Application application;
    private List<TimeSeriesValue> series = new ArrayList<>();

    public ExceptionsPerTime(Application application) {
        this.application = application;
    }

    public ExceptionsPerTime(Application application, List<TimeSeriesValue> timeSeriesValues) {
        this.application = application;
        series.addAll(timeSeriesValues);
        Collections.sort(series);
    }

    public Application getApplication() {
        return application;
    }

    public List<TimeSeriesValue> getTimeValues() {
        return series;
    }
}

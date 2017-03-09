package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationSeries {
    private Application application;
    private List<ApplicationLoad> series = new ArrayList<>();

    public ApplicationSeries(Application application, ApplicationLoad... timeValues) {
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }

    public List<ApplicationLoad> getLoadValues() {
        return series;
    }
}

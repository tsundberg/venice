package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebserviceLoad {
    private Application application;
    private List<WebserviceLoadValue> series = new ArrayList<>();

    public WebserviceLoad(Application application, WebserviceLoadValue... timeValues) {
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }

    public List<WebserviceLoadValue> getLoadValues() {
        return series;
    }
}

package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebserviceLoad {
    private Application application;
    private List<WebserviceValue> series = new ArrayList<>();

    public WebserviceLoad(Application application, WebserviceValue... timeValues) {
        this.application = application;
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }

    public List<WebserviceValue> getLoadValues() {
        return series;
    }
}

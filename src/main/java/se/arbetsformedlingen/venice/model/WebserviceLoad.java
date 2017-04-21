package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WebserviceLoad {
    private List<WebserviceValue> series = new ArrayList<>();

    public WebserviceLoad(WebserviceValue... timeValues) {
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }

    public List<WebserviceValue> getLoadValues() {
        return series;
    }
}

package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationLoad {
    private Application application;
    private List<HostValue> series = new ArrayList<>();

    public ApplicationLoad(Application application, HostValue... timeValues) {
        this.application = application;
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }

    public List<HostValue> getLoadValues() {
        return series;
    }
}

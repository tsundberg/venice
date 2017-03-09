package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationLoad {
    private Application application;
    private List<HostLoadValue> series = new ArrayList<>();

    public ApplicationLoad(Application application, HostLoadValue... timeValues) {
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }

    public List<HostLoadValue> getLoadValues() {
        return series;
    }
}

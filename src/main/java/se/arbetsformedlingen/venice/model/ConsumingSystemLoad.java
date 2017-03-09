package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsumingSystemLoad {
    private Application application;
    private List<ConsumingSystemValue> series = new ArrayList<>();

    public ConsumingSystemLoad(Application application, ConsumingSystemValue... consumingSystemValues) {
        Collections.addAll(series, consumingSystemValues);
        Collections.sort(series);
    }

    public List<ConsumingSystemValue> getConsumingSystems() {
        return series;
    }
}

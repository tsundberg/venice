package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsumingSystemLoad {
    private Application application;
    private List<ConsumingSystemValue> series = new ArrayList<>();

    public ConsumingSystemLoad(Application application, List<ConsumingSystemValue> consumingSystemValues) {
        this.application = application;
        series.addAll(consumingSystemValues);
        Collections.sort(series);
    }

    public List<ConsumingSystemValue> getConsumingSystems() {
        return series;
    }
}

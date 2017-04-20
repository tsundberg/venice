package se.arbetsformedlingen.venice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationLoad {
    private List<HostValue> series = new ArrayList<>();

    public ApplicationLoad(HostValue... timeValues) {
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }

    public List<HostValue> getLoadValues() {
        return series;
    }
}

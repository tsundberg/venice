package se.arbetsformedlingen.venice.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeSeries {
    private List<TimeValue> series = new ArrayList<>();

    public TimeSeries(TimeValue... timeValues) {
        Collections.addAll(series, timeValues);
        Collections.sort(series);
    }
}

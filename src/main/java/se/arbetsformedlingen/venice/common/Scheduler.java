package se.arbetsformedlingen.venice.common;

import java.util.concurrent.TimeUnit;

public interface Scheduler {
    void startChecking(int period, TimeUnit unit);
}

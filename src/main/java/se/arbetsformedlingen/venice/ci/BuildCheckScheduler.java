package se.arbetsformedlingen.venice.ci;

import se.arbetsformedlingen.venice.common.Scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BuildCheckScheduler implements Scheduler {
    private LatestBuildStatuses latestProbeStatuses = new LatestBuildStatuses();

    @Override
    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        CheckBuild checkBuild = new CheckBuild();
        BuildChecker checker = new BuildChecker(checkBuild, latestProbeStatuses);
        scheduler.scheduleAtFixedRate(checker, 1, period, unit);
    }
}

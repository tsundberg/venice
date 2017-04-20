package se.arbetsformedlingen.venice.ci;

import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BuildCheckScheduler implements Scheduler {
    private LatestBuildStatuses latestProbeStatuses = new LatestBuildStatuses();
    private TPJAdmin tpjAdmin;
    private Configuration configuration;

    public BuildCheckScheduler(TPJAdmin tpjAdmin, Configuration configuration) {
        this.tpjAdmin = tpjAdmin;
        this.configuration = configuration;
    }

    @Override
    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        CheckBuild checkBuild = new CheckBuild(tpjAdmin, configuration);
        BuildChecker checker = new BuildChecker(checkBuild, latestProbeStatuses);
        scheduler.scheduleAtFixedRate(checker, 1, period, unit);
    }
}

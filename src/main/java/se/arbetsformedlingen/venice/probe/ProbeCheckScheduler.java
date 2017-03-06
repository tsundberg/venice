package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Applications;
import se.arbetsformedlingen.venice.common.Host;
import se.arbetsformedlingen.venice.common.Scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProbeCheckScheduler implements Scheduler {
    private LatestProbeStatuses latestProbeStatuses = new LatestProbeStatuses();
    private List<ProbeChecker> probes = new LinkedList<>();

    public ProbeCheckScheduler() {
        addProbes();
    }

    private void addProbes() {
        for (Application app : Applications.getApplications()) {
            for (Host host : app.getHosts()) {
                CheckProbe probe = new CheckProbe(host, app);
                probes.add(new ProbeChecker(probe, latestProbeStatuses));
            }
        }
    }

    @Override
    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);
        for (ProbeChecker probe : probes) {
            scheduler.scheduleAtFixedRate(probe, 1, period, unit);
        }
    }
}

package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.common.Server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProbeCheckScheduler implements Scheduler {
    private LatestProbeStatuses latestProbeStatuses = new LatestProbeStatuses();
    private List<ProbeChecker> probes = new LinkedList<>();

    public ProbeCheckScheduler(List<Server> servers) {
        addProbes(servers);
    }

    private void addProbes(List<Server> servers) {
        for (Server server : servers) {
            CheckProbe probe = new CheckProbe(server);
            probes.add(new ProbeChecker(probe, latestProbeStatuses));
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

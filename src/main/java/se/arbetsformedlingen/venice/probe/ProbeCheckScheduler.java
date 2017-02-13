package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Hosts;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProbeCheckScheduler {
    private LatestProbeStatuses latestProbeStatuses = new LatestProbeStatuses();
    private List<ProbeChecker> probes = new LinkedList<>();

    public ProbeCheckScheduler() {
        addProbes();
    }

    private void addProbes() {
        CheckProbe gfr_U1 = new CheckProbe(new Host(Hosts.GFR_U1), new Application("gfr"));
        probes.add(new ProbeChecker(gfr_U1, latestProbeStatuses));
    }

    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);
        for (ProbeChecker probe : probes) {
            scheduler.scheduleAtFixedRate(probe, 1, period, unit);
        }
    }

}

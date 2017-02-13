package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;
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
        Application gfr = new Application("gfr");
        for (String hostName : Hosts.getGFRHosts()) {
            CheckProbe probe = new CheckProbe(new Host(hostName), gfr);
            probes.add(new ProbeChecker(probe, latestProbeStatuses));
        }

        Application geo = new Application("geo");
        for (String hostName : Hosts.getGFRHosts()) {
            CheckProbe probe = new CheckProbe(new Host(hostName), geo);
            probes.add(new ProbeChecker(probe, latestProbeStatuses));
        }
    }

    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);
        for (ProbeChecker probe : probes) {
            scheduler.scheduleAtFixedRate(probe, 1, period, unit);
        }
    }

}

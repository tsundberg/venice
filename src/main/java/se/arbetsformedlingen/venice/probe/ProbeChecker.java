package se.arbetsformedlingen.venice.probe;

import java.util.concurrent.CompletableFuture;

public class ProbeChecker implements Runnable {
    private LatestProbeStatuses latestProbeStatuses;
    private CompletableFuture<Void> worker;
    private CheckProbe checkProbe;

    ProbeChecker(CheckProbe checkProbe, LatestProbeStatuses latestProbeStatuses) {
        this.checkProbe = checkProbe;
        this.latestProbeStatuses = latestProbeStatuses;
    }

    @Override
    public void run() {
        worker = CompletableFuture.supplyAsync(checkProbe)
                .thenAccept(result -> latestProbeStatuses.addStatus(result));
    }

    boolean isWorking() {
        return worker != null && !worker.isDone();
    }
}

package se.arbetsformedlingen.venice.probe;

import java.util.concurrent.CompletableFuture;

public class ProbeChecker implements Runnable {
    private LatestsProbeStatuses latestsProbeStatuses;
    private CompletableFuture<Void> worker;
    private CheckProbe checkProbe;

    ProbeChecker(CheckProbe checkProbe, LatestsProbeStatuses latestsProbeStatuses) {
        this.checkProbe = checkProbe;
        this.latestsProbeStatuses = latestsProbeStatuses;
    }

    @Override
    public void run() {
        worker = CompletableFuture.supplyAsync(checkProbe)
                .thenAccept(result -> latestsProbeStatuses.addStatus(result));
    }

    boolean isWorking() {
        return worker != null && !worker.isDone();
    }
}

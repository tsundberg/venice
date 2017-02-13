package se.arbetsformedlingen.venice.probe;

import java.util.concurrent.CompletableFuture;

public class ProbeChecker implements Runnable {
    private ProbeStatus probeStatus;
    private CompletableFuture<Void> worker;
    private CheckProbe checkProbe;

    ProbeChecker(CheckProbe checkProbe, ProbeStatus probeStatus) {
        this.checkProbe = checkProbe;
        this.probeStatus = probeStatus;
    }

    @Override
    public void run() {
        worker = CompletableFuture.supplyAsync(checkProbe)
                .thenAccept(result -> probeStatus.addStatus(result));
    }

    boolean isWorking() {
        return worker != null && !worker.isDone();
    }
}

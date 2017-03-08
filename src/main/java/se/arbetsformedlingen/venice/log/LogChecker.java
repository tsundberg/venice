package se.arbetsformedlingen.venice.log;

import java.util.concurrent.CompletableFuture;

// todo should perhaps be internal for the scheduler?
// The only reason we need a Runnable is because the scheduleAtFixedRate is stupid.
public class LogChecker implements Runnable {
    private LatestLogs latestLogs;
    private CompletableFuture<Void> worker;
    private CheckLogs checkLogs;

    public LogChecker(CheckLogs checkLogs) {
        this.checkLogs = checkLogs;
    }

    @Override
    public void run() {
        worker = CompletableFuture.supplyAsync(checkLogs)
                .thenAccept(result -> latestLogs.addLog(result));
    }

    boolean isWorking() {
        return worker != null && !worker.isDone();
    }
}

package se.arbetsformedlingen.venice.build;

import java.util.concurrent.CompletableFuture;

public class BuildChecker implements Runnable {
    private LatestBuildStatuses latestBuildStatuses;
    private CompletableFuture<Void> worker;
    private CheckBuild checkBuild;

    BuildChecker(CheckBuild checkBuild, LatestBuildStatuses latestBuildStatuses) {
        this.latestBuildStatuses = latestBuildStatuses;
        this.checkBuild = checkBuild;
    }

    @Override
    public void run() {
        worker = CompletableFuture.supplyAsync(checkBuild)
                .thenAccept(result -> latestBuildStatuses.addStatus(result));

    }

    boolean isWorking() {
        return worker != null && !worker.isDone();
    }
}

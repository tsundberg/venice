package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.log.elasticsearch.FindExceptions;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogcheckScheduler implements Scheduler {
    private LatestLogs latestLogs = new LatestLogs();
    private List<Checker> checkers = new LinkedList<>();

    public LogcheckScheduler() {
        checkers.add(new Checker(new FindExceptions()));
    }

    @Override
    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16);

        for (Checker checker : checkers) {
            scheduler.scheduleAtFixedRate(checker, 1, period, unit);
        }
    }

    private class Checker implements Runnable {
        private FindExceptions findExceptions;

        private Checker(FindExceptions findExceptions) {
            this.findExceptions = findExceptions;
        }

        @Override
        public void run() {
            CompletableFuture.supplyAsync(findExceptions)
                    .thenAccept(result -> latestLogs.addLog(result));
        }
    }
}

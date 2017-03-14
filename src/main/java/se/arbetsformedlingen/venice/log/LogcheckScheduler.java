package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.log.elasticsearch.ElasticSearchClient;
import se.arbetsformedlingen.venice.log.elasticsearch.FindApplicationLoad;
import se.arbetsformedlingen.venice.log.elasticsearch.FindExceptions;
import se.arbetsformedlingen.venice.log.elasticsearch.FindWebserviceLoad;
import se.arbetsformedlingen.venice.model.Application;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class LogcheckScheduler implements Scheduler {
    private LatestLogs latestLogs = new LatestLogs();
    private List<Checker> checkers = new LinkedList<>();

    public LogcheckScheduler() {
        checkers.add(new Checker(new FindExceptions(new Application("gfr"))));
        checkers.add(new Checker(new FindExceptions(new Application("geo"))));
        checkers.add(new Checker(new FindExceptions(new Application("cpr"))));
        checkers.add(new Checker(new FindExceptions(new Application("agselect"))));

        checkers.add(new Checker(new FindApplicationLoad(new Application("gfr"))));
        checkers.add(new Checker(new FindApplicationLoad(new Application("geo"))));
        checkers.add(new Checker(new FindApplicationLoad(new Application("cpr"))));
        checkers.add(new Checker(new FindApplicationLoad(new Application("agselect"))));

        checkers.add(new Checker(new FindWebserviceLoad(new Application("gfr"))));
        checkers.add(new Checker(new FindWebserviceLoad(new Application("geo"))));
        checkers.add(new Checker(new FindWebserviceLoad(new Application("cpr"))));
    }

    @Override
    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16);

        for (Checker checker : checkers) {
            scheduler.scheduleAtFixedRate(checker, 1, period, unit);
        }
    }

    private class Checker implements Runnable {
        private Supplier<LogResponse> findExceptions;

        private Checker(Supplier<LogResponse> findExceptions) {
            this.findExceptions = findExceptions;
        }

        @Override
        public void run() {
            CompletableFuture.supplyAsync(findExceptions)
                    .thenAccept(result -> latestLogs.addLog(result));
        }
    }
}

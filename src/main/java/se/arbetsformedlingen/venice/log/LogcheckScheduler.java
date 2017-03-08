package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Scheduler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogcheckScheduler implements Scheduler {
    private LatestLogs latestProbeStatuses = new LatestLogs();
    private List<LogChecker> checkers = new LinkedList<>();

    public LogcheckScheduler() {
        checkers.add(new LogChecker(new CheckLogs()));
    }

    @Override
    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);

        for (LogChecker checker : checkers) {
            scheduler.scheduleAtFixedRate(checker, 1, period, unit);
        }
    }
}

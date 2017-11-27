package se.arbetsformedlingen.venice.log;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.log.elasticsearch.*;
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

    public LogcheckScheduler(Configuration configuration) {
        Settings settings = FatElasticSearchClient.getSettings();
        Client client = FatElasticSearchClient.getClient(settings);

        // todo read from configuration
        String host = "elk.arbetsformedlingen.se";
        String port = "9200";
        ElasticSearchClient elasticSearchClient = new ElasticSearchClient(host, port);

        checkers.add(new Checker(new FindExceptions(elasticSearchClient, new Application("gfr"), configuration)));
        checkers.add(new Checker(new FindExceptions(elasticSearchClient, new Application("geo"), configuration)));
        checkers.add(new Checker(new FindExceptions(elasticSearchClient, new Application("cpr"), configuration)));
        checkers.add(new Checker(new FindExceptions(elasticSearchClient, new Application("agselect"), configuration)));

        checkers.add(new Checker(new FindHostLoad(elasticSearchClient, new Application("gfr"), configuration)));
        checkers.add(new Checker(new FindHostLoad(elasticSearchClient, new Application("geo"), configuration)));
        checkers.add(new Checker(new FindHostLoad(elasticSearchClient, new Application("cpr"), configuration)));
        checkers.add(new Checker(new FindHostLoad(elasticSearchClient, new Application("agselect"), configuration)));

        checkers.add(new Checker(new FindWebserviceLoad(client, new Application("gfr"))));
        checkers.add(new Checker(new FindWebserviceLoad(client, new Application("geo"))));
        checkers.add(new Checker(new FindWebserviceLoad(client, new Application("cpr"))));

        checkers.add(new Checker(new FindConsumingSystemLoad(client, new Application("gfr"))));
        checkers.add(new Checker(new FindConsumingSystemLoad(client, new Application("geo"))));
        checkers.add(new Checker(new FindConsumingSystemLoad(client, new Application("cpr"))));
        checkers.add(new Checker(new FindConsumingSystemLoad(client, new Application("agselect"))));
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

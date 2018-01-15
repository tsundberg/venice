package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Scheduler;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.model.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
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
        String[] hosts = new String[] {
            "l7700746.wpa.ams.se",
            "l7700747.wpa.ams.se",
            "l7700770.wpa.ams.se"
        };

        String gfrFormat = "http://${host}:${port}/service/foretag/${version}/debug/servicelog";
        String[] gfrVersions = new String[] { "3.0", "4.0", "5.0", "6.0" };

        String geoFormat = "http://${host}:${port}/geo/debug/resourcelog";
        String[] geoVersions = new String[] { "v1" };

        checkers.add(new Checker(new Application("gfr"), gfrFormat, hosts, 8580, gfrVersions));
        checkers.add(new Checker(new Application("geo"), geoFormat, hosts, 8180, geoVersions));

        String[] cprHosts = new String[] {
            "l7700767.wpa.ams.se",
            "l7700775.wpa.ams.se",
            "l7700772.wpa.ams.se"
        };

        String cprFormat = "http://${host}:${port}/PlatsService/ws/debug/servicelog";
        String[] cprVersions = new String[] { "v1 "};

        checkers.add(new Checker(new Application("cpr"), cprFormat, cprHosts, 8580, cprVersions));

        String[] agselectHosts = new String[] {
            "l7700843.wpa.ams.se",
            "l7700842.wpa.ams.se",
            "l7700841.wpa.ams.se"
        };

        String agselectFormat = "http://${host}:${port}/marknadsanalys/web/debug/pathlog";
        String[] agselectVersions = new String[] { "v1 "};

        checkers.add(new Checker(new Application("agselect"), agselectFormat, agselectHosts, 8280, agselectVersions));
    }

    @Override
    public void startChecking(int period, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16);

        for (Checker checker : checkers) {
            scheduler.scheduleAtFixedRate(checker, 1, period, unit);
        }
    }

    private class Checker implements Runnable {
        private Application app;
        private String format;
        private String[] hosts;
        private int port;
        private String[] versions;

        private Checker(Application app, String format, String[] hosts, int port, String[] versions) {
            this.app = app;
            this.format = format;
            this.hosts = hosts;
            this.port = port;
            this.versions = versions;
        }

        @Override
        public void run() {
            MonitorClient client = new MonitorClient(format, hosts, port, versions);

            try {
                MonitorResult result = client.fetch();

                List<ConsumingSystemValue> values = result.getConsumingSystemLoad();

                List<HostValue> hostValues = result.getHostLoad();

                ConsumingSystemLoad load = new ConsumingSystemLoad(app, values);
                ApplicationLoad hostLoad = new ApplicationLoad(hostValues.toArray(new HostValue[hostValues.size()]));

                latestLogs.addLog(new LogResponse(app, new LogType("consuming-system"), load));
                latestLogs.addLog(new LogResponse(app, new LogType("application-load"), hostLoad));
            } catch (java.io.IOException e) {
                System.err.println("Failed to load data for " + app.getName() + ": " + e.getMessage());
            }
        }
    }
}

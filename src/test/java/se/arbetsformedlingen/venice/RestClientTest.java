package se.arbetsformedlingen.venice;


import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RestClientTest {

    @Test
    @Ignore
    public void rest_client() throws Exception {
        String uri = "http://gfr.u1/wildfly05/jolokia/read/af-probe:probe=UgkForetagProbe/";

        String user = System.getenv("PROBE_USER");
        String password = System.getenv("PROBE_PASSWORD");

        Executor executor = Executor.newInstance()
                .auth(user, password);

        CompletableFuture<Object> foo = CompletableFuture.supplyAsync(() -> {
            String r = "Failure";
            try {
                r = executor.execute(Request.Get(uri))
                        .returnContent().asString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return r;
        });

        while (!foo.isDone()) {
            Thread.sleep(50);
        }

        System.out.println(foo.get());
    }

    @Test
    @Ignore
    public void schedule_tasks() throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable beeper = () -> {
            try {
                if (Math.random() > 0.5) {
                    System.out.println("beep");
                } else {
                    throw new RuntimeException("Ooops");
                }
            } catch (Throwable t) {
                System.out.println(t.getMessage());
            }
        };

        scheduler.scheduleAtFixedRate(beeper, 1, 1, SECONDS);

        pause();

        System.out.println("Done");
    }

    @Test
    @Ignore
    public void schedule_many_executions() throws Exception {
        ProbeStatus probeStatus = new ProbeStatus();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        String[] hostNames = {"foo", "bar", "foobar"};

        for (String hostName : hostNames) {
            ProbeChecker probeChecker = new ProbeChecker(hostName, probeStatus);
            scheduler.scheduleAtFixedRate(probeChecker, 0, 1, SECONDS);
        }

        pause();

        for (String host : probeStatus.getHosts()) {
            System.out.println("Status for " + host + " is " + probeStatus.getStatus(host));
        }

        System.out.println("Done - updated statuses " + probeStatus.calledTimes());

    }

    private void pause() throws InterruptedException {
        Thread.sleep(6 * 1000);
    }
}

class ProbeChecker implements Runnable {
    private String hostName;
    private ProbeStatus probeStatus;

    ProbeChecker(String hostName, ProbeStatus probeStatus) {
        this.hostName = hostName;
        this.probeStatus = probeStatus;
    }

    @Override
    public void run() {
        CheckProbeFor checkProbeFor = new CheckProbeFor(hostName);
        CompletableFuture.supplyAsync(checkProbeFor)
                .thenAccept(result -> {
                    probeStatus.addStatus(hostName, result);
                    System.out.println(result);
                });
        System.out.println("submitted check");
    }
}

class CheckProbeFor implements java.util.function.Supplier<String> {
    private String hostname;

    CheckProbeFor(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String get() {
        long time = 0;
        try {
            time = randomPause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Checking " + hostname + " took " + time + " ms");
        return "Online";
    }

    private long randomPause() throws InterruptedException {
        long time = (long) (Math.random() * 6000);
        Thread.sleep(time);
        return time;
    }

}

class ProbeStatus {
    private static ConcurrentMap<String, String> probeStatuses = new ConcurrentHashMap<>();
    private static int times = 0;

    void addStatus(String host, String status) {
        synchronized (this) {
            times++;
        }

        probeStatuses.put(host, status);
    }

    Set<String> getHosts() {
        return probeStatuses.keySet();
    }

    String getStatus(String host) {
        return probeStatuses.get(host);
    }

    int calledTimes() {
        return times;
    }
}

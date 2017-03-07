package se.arbetsformedlingen.venice;


import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RestClientIT {

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

    private void pause() throws InterruptedException {
        Thread.sleep(6 * 1000);
    }
}

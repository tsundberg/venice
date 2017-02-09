package se.arbetsformedlingen.venice;


import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RestClientTest {

    @Test
    @Ignore
    public void rest_client() throws Exception {
        String uri = "http://gfr.u1/wildfly05/jolokia/read/af-probe:probe=UgkForetagProbe/";

        String user = "unset";
        String password = "secret";
        Executor executor = Executor.newInstance()
                .auth(user, password);

        String res = executor.execute(Request.Get(uri))
                .returnContent().asString();

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
            System.out.print(".");
            Thread.sleep(50);
        }

        assertThat(foo.get()).isEqualTo("Hello");
    }

}

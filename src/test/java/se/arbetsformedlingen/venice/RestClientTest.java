package se.arbetsformedlingen.venice;


import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.IOException;

public class RestClientTest {

    @Test
    public void rest_client() throws InterruptedException, IOException {
        String uri = "https://jsonplaceholder.typicode.com/posts/1";

        String res = Request.Get(uri)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute().returnContent().asString();

        System.out.println(res);

        Executor executor = Executor.newInstance()
                .auth("username", "password");

        res = executor.execute(Request.Get(uri))
                .returnContent().asString();

        System.out.println(res);
    }
}

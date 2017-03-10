package se.arbetsformedlingen.venice.log;

import org.junit.Test;
import spark.Request;
import spark.RequestStub;

public class LogControllerTest {

    @Test
    public void get_gfr_exceptions() {
        Request request = new RequestStub.RequestBuilder()
                .params("application", "gfr")
                .params("logType", "exceptions")
                .build();

        String actual = LogController.getLogs(request, null);


        System.out.println(actual);
    }
}

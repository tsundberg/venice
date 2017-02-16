package se.arbetsformedlingen.venice.probe;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;
import se.arbetsformedlingen.venice.common.Status;
import se.arbetsformedlingen.venice.common.Version;

import java.util.HashMap;
import java.util.Map;

public class CheckProbe implements java.util.function.Supplier<ProbeResponse> {
    private final Host host;
    private final Application application;

    CheckProbe(Host host, Application application) {
        this.host = host;
        this.application = application;
    }

    @Override
    public ProbeResponse get() {
        String uri = getUri();
        Executor executor = getAuthenticatedExecutor();

        String result;
        try {
            result = executor.execute(Request.Get(uri))
                    .returnContent()
                    .asString();

            return ProbeResponseParser.parse(result);
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    private String getUri() {
        String port = application.getPort();
        String probeName = application.getProbeName();

        return "http://" + host + port + "/jolokia/read/af-probe:probe=" + probeName + "/";
    }

    private Executor getAuthenticatedExecutor() {
        String user = System.getenv("PROBE_USER");
        String password = System.getenv("PROBE_PASSWORD");

        return Executor.newInstance()
                .auth(user, password);
    }

    private ProbeResponse errorResponse(Exception e) {
        Status status = new Status(e.getMessage());
        Version version = new Version("Unknown");
        return new ProbeResponse(application, host, status, version);
    }
}

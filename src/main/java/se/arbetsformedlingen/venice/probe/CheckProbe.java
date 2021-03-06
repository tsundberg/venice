package se.arbetsformedlingen.venice.probe;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import se.arbetsformedlingen.venice.model.*;

public class CheckProbe implements java.util.function.Supplier<ProbeResponse> {
    private ApplicationServer applicationServer;

    CheckProbe(ApplicationServer applicationServer) {
        this.applicationServer = applicationServer;
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

            return ProbeResponseParser.parse(applicationServer, result);
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    String getUri() {
        Probe probe = applicationServer.getProbe();
        Host host = applicationServer.getHost();
        Port port = applicationServer.getPort();

        return "http://" + host + ":" + port + "/jolokia/read/af-probe:probe=" + probe + "/";
    }

    private Executor getAuthenticatedExecutor() {
        String user = System.getenv("PROBE_USER");
        String password = System.getenv("PROBE_PASSWORD");

        return Executor.newInstance()
                .auth(user, password);
    }

    ProbeResponse errorResponse(Exception e) {
        Status status = new Status(e.getMessage());
        Version version = new Version("Unknown");
        return new ProbeResponse(applicationServer, status, version);
    }
}

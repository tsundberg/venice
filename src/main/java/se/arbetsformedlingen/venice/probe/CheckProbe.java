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

    private Map<Application, String> ports = new HashMap<>();
    private Map<Application, String> probeNames = new HashMap<>();

    CheckProbe(Host host, Application application) {
        this.host = host;
        this.application = application;

        addPorts();
        addProbeNames();
    }

    private void addPorts() {
        ports.put(new Application("gfr"), "/wildfly05");
        ports.put(new Application("geo"), "/wildfly01");
    }

    private void addProbeNames() {
        probeNames.put(new Application("gfr"), "UgkForetagProbe");
        probeNames.put(new Application("geo"), "UgkGeoProbe");
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
        String port = ports.get(application);
        String ugkForetagProbe = probeNames.get(application);

        return "http://" + host + port + "/jolokia/read/af-probe:probe=" + ugkForetagProbe + "/";
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

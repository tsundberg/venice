package se.arbetsformedlingen.venice.probe;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
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
    }

    private void addProbeNames() {
        probeNames.put(new Application("gfr"), "UgkForetagProbe");
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
        } catch (IOException e) {
            throw new CheckProbeException(e.getMessage(), e);
        }

        return ProbeResponseParser.parse(result);
    }

    private String getUri() {
        String hostName = host.getName();
        String port = ports.get(application);
        String ugkForetagProbe = probeNames.get(application);

        return "http://" + hostName + port + "/jolokia/read/af-probe:probe=" + ugkForetagProbe + "/";
    }

    private Executor getAuthenticatedExecutor() {
        String user = System.getenv("PROBE_USER");
        String password = System.getenv("PROBE_PASSWORD");

        return Executor.newInstance()
                .auth(user, password);
    }
}

package se.arbetsformedlingen.venice.probe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class LatestProbeStatuses {
    private static ConcurrentMap<String, ProbeResponse> probeStatuses = new ConcurrentHashMap<>();

    void addStatus(ProbeResponse value) {
        String key = generateKey(value.getHost(), value.getApplication());

        probeStatuses.put(key, value);
    }

    ProbeResponse getStatus(Host host, Application application) {
        String key = generateKey(host, application);
        return probeStatuses.get(key);
    }

    private String generateKey(Host host, Application application) {
        return host.getName() + "->" + application.getApplicationName();
    }
}

package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;
import se.arbetsformedlingen.venice.common.Status;
import se.arbetsformedlingen.venice.common.Version;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class LatestProbeStatuses {
    private static ConcurrentMap<String, ProbeResponse> probeStatuses = new ConcurrentHashMap<>();

    void addStatus(ProbeResponse value) {
        String key = generateKey(value.getHostName(), value.getApplicationName());

        probeStatuses.put(key, value);
    }

    ProbeResponse getStatus(Host host, Application application) {
        String hostName = host.getName();
        String applicationName = application.getApplicationName();

        String key = generateKey(hostName, applicationName);

        if (probeStatuses.containsKey(key)) {
            return probeStatuses.get(key);
        }

        Status status = new Status("Unknown");
        Version version = new Version("Unknown");
        return new ProbeResponse(application, host, status, version);
    }

    private String generateKey(String host, String application) {
        return host + "->" + application;
    }
}

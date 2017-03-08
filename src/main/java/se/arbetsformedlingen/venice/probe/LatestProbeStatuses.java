package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.ApplicationServer;
import se.arbetsformedlingen.venice.common.Status;
import se.arbetsformedlingen.venice.common.Version;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class LatestProbeStatuses {
    private static ConcurrentMap<ApplicationServer, ProbeResponse> probeStatuses = new ConcurrentHashMap<>();

    void addStatus(ProbeResponse value) {
        probeStatuses.put(value.getApplicationServer(), value);
    }

    ProbeResponse getStatus(ApplicationServer applicationServer) {
        if (probeStatuses.containsKey(applicationServer)) {
            return probeStatuses.get(applicationServer);
        }

        Status status = new Status("Unknown");
        Version version = new Version("Unknown");
        return new ProbeResponse(applicationServer, status, version);
    }
}

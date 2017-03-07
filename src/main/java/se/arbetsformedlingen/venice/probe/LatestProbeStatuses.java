package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Server;
import se.arbetsformedlingen.venice.common.Status;
import se.arbetsformedlingen.venice.common.Version;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class LatestProbeStatuses {
    private static ConcurrentMap<Server, ProbeResponse> probeStatuses = new ConcurrentHashMap<>();

    void addStatus(ProbeResponse value) {
        probeStatuses.put(value.getServer(), value);
    }

    ProbeResponse getStatus(Server server) {
        if (probeStatuses.containsKey(server)) {
            return probeStatuses.get(server);
        }

        Status status = new Status("Unknown");
        Version version = new Version("Unknown");
        return new ProbeResponse(server, status, version);
    }
}

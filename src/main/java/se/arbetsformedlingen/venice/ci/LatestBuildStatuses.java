package se.arbetsformedlingen.venice.ci;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class LatestBuildStatuses {
    private static ConcurrentMap<String, BuildResponse> buildStatuses = new ConcurrentHashMap<>();

    void addStatus(List<BuildResponse> value) {
        for (BuildResponse response : value) {
            String key = response.getName();

            buildStatuses.put(key, response);
        }
    }

    List<BuildResponse> getStatuses() {
        List<BuildResponse> jobs = new LinkedList<>();

        Set<String> keys = buildStatuses.keySet();

        for (String key : keys) {
            BuildResponse job = buildStatuses.get(key);
            jobs.add(job);
        }

        return jobs;
    }
}

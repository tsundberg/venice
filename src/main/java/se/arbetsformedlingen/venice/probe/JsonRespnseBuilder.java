package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Server;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Environment;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.json.JSONObject;
import org.json.JSONArray;

public class JsonRespnseBuilder {
    private LatestProbeStatuses statuses;

    public JsonRespnseBuilder(LatestProbeStatuses statuses) {
        this.statuses = statuses;
    }

    public String build(List<Server> servers) {
        Set<Application> apps = new HashSet<>();
        servers.stream().forEach(srv -> {
            apps.add(srv.getApplication());
        });

        Set<Environment> envs = new HashSet<>();
        servers.stream().forEach(srv -> {
            envs.add(srv.getEnvironment());
        });

        JSONArray response = new JSONArray();
        for (Application app : apps) {
            JSONObject appObj = new JSONObject();
            appObj.put("name", app.getName());

            JSONArray envList = new JSONArray();
            for (Environment env : envs) {
                JSONObject envObj = new JSONObject();
                envObj.put("name", env.getName());

                JSONObject serverList = new JSONObject();
                for (Server server : servers) {
                    if (!app.equals(server.getApplication())) {
                        continue;
                    }
                    if (!env.equals(server.getEnvironment())) {
                        continue;
                    }

                    ProbeResponse probeRes = statuses.getStatus(server);

                    JSONObject serverObj = new JSONObject();
                    serverObj.put("status", probeRes.getStatus());
                    serverObj.put("version", probeRes.getVersion());

                    serverList.put(server.getHost().toString(), serverObj);
                }

                envObj.put("servers", serverList);

                envList.put(envObj);
            }

            response.put(appObj);
        }

        return response.toString();
    }
}

package se.arbetsformedlingen.venice.probe;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Environment;
import se.arbetsformedlingen.venice.common.Server;

import java.util.LinkedList;
import java.util.List;

public class JsonRespnseBuilder {
    private LatestProbeStatuses statuses;

    public JsonRespnseBuilder(LatestProbeStatuses statuses) {
        this.statuses = statuses;
    }

    public String build(List<Server> servers) {
        List<Application> apps = new LinkedList<>();
        servers.forEach(srv -> {
            Application application = srv.getApplication();
            if (!apps.contains(application)) {
                apps.add(application);
            }
        });

        List<Environment> envs = new LinkedList<>();
        servers.forEach(srv -> {
            Environment environment = srv.getEnvironment();
            if (!envs.contains(environment)) {
                envs.add(environment);
            }
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
            appObj.put("environments", envList);

            response.put(appObj);
        }

        return response.toString();
    }
}

package se.arbetsformedlingen.venice.probe;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Environment;
import se.arbetsformedlingen.venice.common.Server;

import java.util.LinkedList;
import java.util.List;

class JsonResponseBuilder {
    private LatestProbeStatuses statuses;

    JsonResponseBuilder(LatestProbeStatuses statuses) {
        this.statuses = statuses;
    }

    String build(List<Server> servers) {
        List<Application> applications = getApplications(servers);
        List<Environment> environments = getEnvironments(servers);

        JSONArray response = new JSONArray();
        for (Application application : applications) {
            JSONObject applicationJson = addApplicationName(application.getName());

            JSONArray envList = new JSONArray();
            for (Environment environment : environments) {
                JSONObject environmentJson = addApplicationName(environment.getName());

                JSONObject serverList = new JSONObject();
                for (Server server : servers) {
                    addServer(application, environment, serverList, server);
                }

                environmentJson.put("servers", serverList);
                envList.put(environmentJson);
            }
            applicationJson.put("environments", envList);

            response.put(applicationJson);
        }

        return response.toString();
    }

    private JSONObject addApplicationName(String name) {
        JSONObject appObj = new JSONObject();
        appObj.put("name", name);
        return appObj;
    }

    private void addServer(Application app, Environment env, JSONObject serverList, Server server) {
        if (!app.equals(server.getApplication())) {
            return;
        }
        if (!env.equals(server.getEnvironment())) {
            return;
        }

        ProbeResponse probeRes = statuses.getStatus(server);

        JSONObject appObj = new JSONObject();
        appObj.put("status", probeRes.getStatus());
        appObj.put("version", probeRes.getVersion());

        serverList.put(server.getHost().toString(), appObj);
    }

    private List<Application> getApplications(List<Server> servers) {
        List<Application> applications = new LinkedList<>();
        servers.forEach(srv -> {
            Application application = srv.getApplication();
            if (!applications.contains(application)) {
                applications.add(application);
            }
        });
        return applications;
    }

    private List<Environment> getEnvironments(List<Server> servers) {
        List<Environment> environments = new LinkedList<>();
        servers.forEach(srv -> {
            Environment environment = srv.getEnvironment();
            if (!environments.contains(environment)) {
                environments.add(environment);
            }
        });
        return environments;
    }
}

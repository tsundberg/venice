package se.arbetsformedlingen.venice.probe;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.ApplicationServer;
import se.arbetsformedlingen.venice.model.Environment;

import java.util.LinkedList;
import java.util.List;

class JsonResponseBuilder {
    private LatestProbeStatuses statuses;

    JsonResponseBuilder(LatestProbeStatuses statuses) {
        this.statuses = statuses;
    }

    String build(List<ApplicationServer> applicationServers) {
        List<Application> applications = getApplications(applicationServers);
        List<Environment> environments = getEnvironments(applicationServers);

        JSONArray response = new JSONArray();
        for (Application application : applications) {
            JSONObject applicationJson = addApplicationName(application.getName());

            JSONArray envList = new JSONArray();
            for (Environment environment : environments) {
                JSONObject environmentJson = addApplicationName(environment.getName());

                JSONObject serverList = new JSONObject();
                for (ApplicationServer applicationServer : applicationServers) {
                    addServer(application, environment, serverList, applicationServer);
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

    private void addServer(Application app, Environment env, JSONObject serverList, ApplicationServer applicationServer) {
        if (!app.equals(applicationServer.getApplication())) {
            return;
        }
        if (!env.equals(applicationServer.getEnvironment())) {
            return;
        }

        ProbeResponse probeRes = statuses.getStatus(applicationServer);

        JSONObject appObj = new JSONObject();
        appObj.put("status", probeRes.getStatus());
        appObj.put("version", probeRes.getVersion());

        serverList.put(applicationServer.getHost().toString(), appObj);
    }

    private List<Application> getApplications(List<ApplicationServer> applicationServers) {
        List<Application> applications = new LinkedList<>();
        applicationServers.forEach(srv -> {
            Application application = srv.getApplication();
            if (!applications.contains(application)) {
                applications.add(application);
            }
        });
        return applications;
    }

    private List<Environment> getEnvironments(List<ApplicationServer> applicationServers) {
        List<Environment> environments = new LinkedList<>();
        applicationServers.forEach(srv -> {
            Environment environment = srv.getEnvironment();
            if (!environments.contains(environment)) {
                environments.add(environment);
            }
        });
        return environments;
    }
}

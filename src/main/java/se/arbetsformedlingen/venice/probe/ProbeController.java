package se.arbetsformedlingen.venice.probe;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Applications;
import se.arbetsformedlingen.venice.common.Environment;
import se.arbetsformedlingen.venice.common.Host;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class ProbeController {

    public static String getStatus(Request request, Response response) {
        LatestProbeStatuses statuses = new LatestProbeStatuses();

        consoleLog(statuses);

        JsonResponseBuilder resBuilder = new JsonResponseBuilder(statuses);

        return resBuilder.buildAppList(Applications.getApplications()).toString();
    }

    private static class JsonResponseBuilder {

        private LatestProbeStatuses statuses;

        private JsonResponseBuilder(LatestProbeStatuses statuses) {
            this.statuses = statuses;
        }

        private JSONObject buildServerObject(Application app, Host host) {
            ProbeResponse res = statuses.getStatus(host, app);

            JSONObject obj = new JSONObject();
            obj.put("status", res.getStatus());
            obj.put("version", res.getVersion());

            return obj;
        }

        private JSONObject buildServerList(Application app, Environment env) {
            JSONObject obj = new JSONObject();

            for (Host host : env.getHosts()) {
                obj.put(host.toString(), buildServerObject(app, host));
            }

            return obj;
        }

        private JSONObject buildEnvironmentObject(Application app, Environment env) {
            JSONObject obj = new JSONObject();
            obj.put("name", env.getName());
            obj.put("servers", buildServerList(app, env));

            return obj;
        }

        private JSONArray buildEnvironmentList(Application app) {
            JSONArray arr = new JSONArray();
            for (Environment env : app.getEnvironments()) {
                arr.put(buildEnvironmentObject(app, env));
            }

            return arr;
        }

        private JSONObject buildAppObject(Application app) {
            JSONObject obj = new JSONObject();
            obj.put("name", app.toString());
            obj.put("environments", buildEnvironmentList(app));

            return obj;
        }

        private JSONArray buildAppList(List<Application> applications) {
            JSONArray arr = new JSONArray();
            for (Application app : applications) {
                arr.put(buildAppObject(app));
            }

            return arr;
        }

    }

    private static void consoleLog(LatestProbeStatuses statuses) {
        System.out.println();
        for (Application app : Applications.getApplications()) {
            List<Environment> envs = app.getEnvironments();
            for (Environment env : envs) {
                for (Host host : env.getHosts()) {
                    ProbeResponse resp = statuses.getStatus(host, app);
                    System.out.println(resp);
                }
            }

        }
        System.out.println();
    }

}

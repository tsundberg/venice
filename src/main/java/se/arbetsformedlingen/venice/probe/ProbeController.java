package se.arbetsformedlingen.venice.probe;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Environment;
import se.arbetsformedlingen.venice.common.Host;
import se.arbetsformedlingen.venice.common.Hosts;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProbeController {

    public static String getStatus(Request request, Response response) {
        LatestProbeStatuses statuses = new LatestProbeStatuses();

        consoleLog(statuses);

        JsonResponseBuilder resBuilder = new JsonResponseBuilder(statuses);

        List<Application> apps = new ArrayList<>();
        apps.add(getGfr());
        apps.add(getGeo());

        return resBuilder.buildAppList(apps).toString();
    }

    public static class JsonResponseBuilder {

        private LatestProbeStatuses statuses;

        public JsonResponseBuilder(LatestProbeStatuses statuses) {
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
                obj.put(host.getName(), buildServerObject(app, host));
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
            obj.put("name", app.getApplicationName());
            obj.put("environments", buildEnvironmentList(app));

            return obj;
        }

        public JSONArray buildAppList(List<Application> applications) {
            JSONArray arr = new JSONArray();
            for (Application app : applications) {
                arr.put(buildAppObject(app));
            }

            return arr;
        }

    }

    private static Application getGfr() {
        Application app = new Application("gfr");

        {
            Environment env = new Environment("PROD");
            env.addHost(new Host(Hosts.GFR_PROD1));
            env.addHost(new Host(Hosts.GFR_PROD2));
            env.addHost(new Host(Hosts.GFR_PROD3));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T2");
            env.addHost(new Host(Hosts.GFR_T21));
            env.addHost(new Host(Hosts.GFR_T22));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T1");
            env.addHost(new Host(Hosts.GFR_T1));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("I1");
            env.addHost(new Host(Hosts.GFR_I1));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("U1");
            env.addHost(new Host(Hosts.GFR_U1));
            app.addEnvironment(env);
        }

        return app;
    }

    private static Application getGeo() {
        Application app = new Application("geo");

        {
            Environment env = new Environment("PROD");
            env.addHost(new Host(Hosts.GEO_PROD1));
            env.addHost(new Host(Hosts.GEO_PROD2));
            env.addHost(new Host(Hosts.GEO_PROD3));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T2");
            env.addHost(new Host(Hosts.GEO_T21));
            env.addHost(new Host(Hosts.GEO_T22));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T1");
            env.addHost(new Host(Hosts.GEO_T1));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("I1");
            env.addHost(new Host(Hosts.GEO_I1));
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("U1");
            env.addHost(new Host(Hosts.GEO_U1));
            app.addEnvironment(env);
        }

        return app;
    }

    private static void consoleLog(LatestProbeStatuses statuses) {
        Application gfr = new Application("gfr");
        Application geo = new Application("geo");

        List<Application> applications = new LinkedList<>();
        applications.add(gfr);
        applications.add(geo);

        System.out.println();
        for (Application app : applications) {
            for (String hostName : Hosts.getGFRHosts()) {
                ProbeResponse resp = statuses.getStatus(new Host(hostName), app);
                System.out.println(resp);
            }
        }
        System.out.println();
    }

}

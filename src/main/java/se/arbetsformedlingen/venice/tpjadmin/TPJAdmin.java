package se.arbetsformedlingen.venice.tpjadmin;

import org.apache.http.Header;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicHeader;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Environment;
import se.arbetsformedlingen.venice.common.Server;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TPJAdmin {
    private static List<Server> servers = new CopyOnWriteArrayList<>();
    private static final String host = "l7700759.wpa.ams.se";
    private static final String port = "8180";
    private static final String uri = "/tpjadmin/rest/properties/v0/wildfly/instances/";

    private static Map<String, String> environments = new HashMap<>();
    private static Map<String, String> applications = new HashMap<>();

    static {
        environments.put("T2", "t2");
        environments.put("T1", "t1");
        environments.put("I1", "i1");
        environments.put("UTV", "u1");
        environments.put("PROD", "prod");

        applications.put("foretag", "gfr");
        applications.put("cpr", "cpr");
        applications.put("geo", "geo");
        applications.put("agselect", "agselect");
    }

    public static List<Server> getServers() {
        return servers;
    }

    public static List<Server> prepareServers() {
        Executor executor = Executor.newInstance();

        System.out.println("Fetching servers");
        for (String app : applications.keySet()) {
            for (String env : environments.keySet()) {
                System.out.print(".");
                try {
                    List<Server> srvs = getServers(executor, app, env);
                    servers.addAll(srvs);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        System.out.println();
        System.out.println("Servers fetched");

        Collections.sort(servers);

        return servers;
    }

    public static List<Application> getApplications() {
        List<Application> apps = new LinkedList<>();

        for (String key : applications.keySet()) {
            String applicationName = applications.get(key);
            Application application = new Application(applicationName);
            apps.add(application);
        }

        return apps;
    }

    public static List<Environment> getEnvironments() {
        List<Environment> envs = new LinkedList<>();

        for (String key : environments.keySet()) {
            String envName = environments.get(key);
            Environment environment = new Environment(envName);
            envs.add(environment);
        }

        return envs;
    }

    private static List<Server> getServers(Executor executor, String app, String env) throws IOException {
        String url = "http://" + host + ":" + port + uri + env + "/" + app + "";
        Header pisaHeader = getPisaId();

        String json = executor
                .execute(
                        Request.Get(url)
                                .addHeader(pisaHeader)
                )
                .returnContent()
                .asString();

        String appName = applications.get(app);
        Application application = new Application(appName);

        String envName = environments.get(env);
        Environment environment = new Environment(envName);

        return TPJAdminResponseParser.parse(application, environment, json);
    }

    private static BasicHeader getPisaId() {
        String pisaId = System.getenv("PISAID");
        return new BasicHeader("PISA_ID", pisaId);
    }

}
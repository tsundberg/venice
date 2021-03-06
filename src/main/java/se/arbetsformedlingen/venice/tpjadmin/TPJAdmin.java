package se.arbetsformedlingen.venice.tpjadmin;

import org.apache.http.Header;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicHeader;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.ApplicationServer;
import se.arbetsformedlingen.venice.model.Environment;
import se.arbetsformedlingen.venice.model.Probe;

import java.io.IOException;
import java.util.*;

public class TPJAdmin {
    private static List<ApplicationServer> applicationServers = new LinkedList<>();
    private static Map<String, String> environments = new HashMap<>();
    private static Map<String, String> applications = new HashMap<>();

    static {
        // todo config
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

    private Configuration configuration;

    public TPJAdmin(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<ApplicationServer> prepareApplicationServers() {
        Executor executor = Executor.newInstance();

        System.out.println("Fetching servers");
        for (String app : applications.keySet()) {
            for (String env : environments.keySet()) {
                System.out.print(".");
                try {
                    List<ApplicationServer> srvs = getServers(executor, app, env);
                    applicationServers.addAll(srvs);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        System.out.println();
        System.out.println("Servers fetched");

        Collections.sort(applicationServers);

        return applicationServers;
    }

    public List<Application> getApplications() {
        List<Application> apps = new LinkedList<>();

        for (String key : applications.keySet()) {
            String applicationName = applications.get(key);
            Probe probe = configuration.getProbe(applicationName);
            Application application = new Application(applicationName, probe);
            apps.add(application);
        }

        return apps;
    }

    private List<ApplicationServer> getServers(Executor executor, String app, String env) throws IOException {
        String host = configuration.getTpjAdminHost();
        Integer port = configuration.getTpjAdminPort();
        String uri = configuration.getTpjAdminUri();
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
        Probe probe = configuration.getProbe(appName);
        Application application = new Application(appName, probe);

        String envName = environments.get(env);
        Environment environment = new Environment(envName);

        return TPJAdminResponseParser.parse(application, environment, json);
    }

    private BasicHeader getPisaId() {
        String pisaId = System.getenv("PISAID");
        return new BasicHeader("PISA_ID", pisaId);
    }
}



package se.arbetsformedlingen.venice.common;

import java.util.Arrays;
import java.util.List;

public class Applications {

    public static List<Application> getApplications() {
        return Arrays.asList(gfr(), geo());
    }

    private static Application gfr() {
        Application app = new Application("gfr");

        {
            Environment env = new Environment("PROD");
            env.addHost(Hosts.GFR_PROD1);
            env.addHost(Hosts.GFR_PROD2);
            env.addHost(Hosts.GFR_PROD3);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T2");
            env.addHost(Hosts.GFR_T21);
            env.addHost(Hosts.GFR_T22);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T1");
            env.addHost(Hosts.GFR_T1);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("I1");
            env.addHost(Hosts.GFR_I1);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("U1");
            env.addHost(Hosts.GFR_U1);
            app.addEnvironment(env);
        }

        return app;
    }

    private static Application geo() {
        Application app = new Application("geo");

        {
            Environment env = new Environment("PROD");
            env.addHost(Hosts.GEO_PROD1);
            env.addHost(Hosts.GEO_PROD2);
            env.addHost(Hosts.GEO_PROD3);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T2");
            env.addHost(Hosts.GEO_T21);
            env.addHost(Hosts.GEO_T22);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("T1");
            env.addHost(Hosts.GEO_T1);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("I1");
            env.addHost(Hosts.GEO_I1);
            app.addEnvironment(env);
        }

        {
            Environment env = new Environment("U1");
            env.addHost(Hosts.GEO_U1);
            app.addEnvironment(env);
        }

        return app;
    }
}

package se.arbetsformedlingen.venice.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ApplicationServer implements Comparable<ApplicationServer> {
    private final Application application;
    private final Environment environment;
    private final Host host;
    private final Port port;

    public ApplicationServer(Application application, Environment environment, Host host, Port port) {
        this.application = application;
        this.environment = environment;
        this.host = host;
        this.port = port;
    }

    public Application getApplication() {
        return application;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Host getHost() {
        return host;
    }

    public Port getPort() {
        return port;
    }

    public Probe getProbe() {
        return application.getProbe();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationServer applicationServer = (ApplicationServer) o;
        return Objects.equals(application, applicationServer.application) &&
                Objects.equals(environment, applicationServer.environment) &&
                Objects.equals(host, applicationServer.host) &&
                Objects.equals(port, applicationServer.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, environment, host, port);
    }

    @Override
    public int compareTo(ApplicationServer o) {
        int application = this.application.getName().compareTo(o.application.getName());
        if (application != 0) {
            return application;
        }

        List<String> environmentOrder = Arrays.asList("prod", "t2", "t1", "i1", "u1");
        Integer lhsPos = environmentOrder.indexOf(environment.getName());
        Integer rhsPos = environmentOrder.indexOf(o.environment.getName());
        int env = lhsPos.compareTo(rhsPos);
        if (env != 0) {
            return env;
        }

        int host = getHost().getName().compareTo(o.getHost().getName());
        if (host != 0) {
            return host;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Server{" +
                "application=" + application +
                ", environment=" + environment +
                ", host=" + host +
                ", port=" + port +
                '}';
    }
}

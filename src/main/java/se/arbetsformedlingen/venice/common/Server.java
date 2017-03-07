package se.arbetsformedlingen.venice.common;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// todo rename
public class Server implements Comparable<Server> {
    private final Application application;
    private final Environment environment;
    private final Host host;
    private final Port port;

    public Server(Application application, Environment environment, Host host, Port port) {
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

    public String getApplicationName() {
        return application.getName();
    }

    public String getProbeName() {
        return application.getProbeName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(application, server.application) &&
                Objects.equals(environment, server.environment) &&
                Objects.equals(host, server.host) &&
                Objects.equals(port, server.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, environment, host, port);
    }

    @Override
    public int compareTo(Server o) {
        int application = getApplicationName().compareTo(o.getApplicationName());
        if (application != 0) {
            return application;
        }

        List<String> environmentOrder = Arrays.asList("prod", "t2", "t1", "i1", "u1");
        Integer lhsPos = environmentOrder.indexOf(getEnvironment().getName());
        Integer rhsPos = environmentOrder.indexOf(o.getEnvironment().getName());
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

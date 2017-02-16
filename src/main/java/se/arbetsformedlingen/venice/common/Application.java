package se.arbetsformedlingen.venice.common;

import java.util.*;

public class Application {
    private String application;
    private List<Environment> environments;

    private static Map<String, String> probeNames;
    private static Map<String, String> ports;

    public Application(String application) {
        this.application = application;
        this.environments = new ArrayList<>();

        addPorts();
        addProbes();
    }

    private void addPorts() {
        if (ports == null) {
            ports = new HashMap<>();

            ports.put("gfr", "/wildfly05");
            ports.put("geo", "/wildfly01");
            ports.put("cpr", "/wildfly05");
            ports.put("marknadsanalys", "/wildfly02");
        }
    }

    private void addProbes() {
        if (probeNames == null) {
            probeNames = new HashMap<>();

            probeNames.put("gfr", "UgkForetagProbe");
            probeNames.put("geo", "UgkGeoProbe");
            probeNames.put("cpr", "CprProbe");
            probeNames.put("marknadsanalys", "MarknadsanalysProbe");
        }
    }

    void addEnvironment(Environment env) {
        this.environments.add(env);
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public List<Host> getHosts() {
        List<Host> hosts = new LinkedList<>();
        for (Environment env : getEnvironments()) {
            hosts.addAll(env.getHosts());
        }

        return hosts;
    }

    public String getProbeName() {
        return probeNames.get(application);
    }

    public String getPort() {
        return ports.get(application);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(application, that.application);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application);
    }

    @Override
    public String toString() {
        return application;
    }
}

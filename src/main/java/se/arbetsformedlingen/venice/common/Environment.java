package se.arbetsformedlingen.venice.common;

import java.util.ArrayList;
import java.util.List;

public class Environment {

    private String name;
    private List<Host> hosts;

    public Environment(String name) {
        this.name = name;
        this.hosts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    void addHost(Host host) {
        hosts.add(host);
    }

    public List<Host> getHosts() {
        return hosts;
    }

    @Override
    public String toString() {
        return name;
    }
}

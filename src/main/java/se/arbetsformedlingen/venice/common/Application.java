package se.arbetsformedlingen.venice.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Application {
    private String application;
    private List<Environment> environments;

    public Application(String application) {
        this.application = application;
        this.environments = new ArrayList<>();
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

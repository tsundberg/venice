package se.arbetsformedlingen.venice.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application {
    private String application;
    private List<Environment> environments;

    public Application(String application) {
        this.application = application;
        this.environments = new ArrayList<>();
    }

    public String getApplicationName() {
        return application;
    }

    public void addEnvironment(Environment env) {
        this.environments.add(env);
    }

    public List<Environment> getEnvironments() {
        return environments;
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
        return "Application{" +
                "application='" + application + '\'' +
                '}';
    }
}

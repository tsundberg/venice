package se.arbetsformedlingen.venice.model;

import java.util.Objects;

public class Application {
    private String name;
    private Probe probe;

    public Application(String name) {
        this.name = name;
    }

    public Application(String name, Probe probe) {
        this.name = name;
        this.probe = probe;
    }

    public Probe getProbe() {
        return probe;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

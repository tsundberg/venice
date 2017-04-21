package se.arbetsformedlingen.venice.model;

import java.util.Objects;

public class Probe {
    private String probe;

    public Probe(String probe) {
        this.probe = probe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Probe probe1 = (Probe) o;
        return Objects.equals(probe, probe1.probe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(probe);
    }

    @Override
    public String toString() {
        return probe;
    }
}

package se.arbetsformedlingen.venice.model;

import java.util.Objects;

public class Host {
    private String host;

    public Host(String host) {
        this.host = host;
    }

    public String getName() {
        return host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host1 = (Host) o;
        return Objects.equals(host, host1.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host);
    }

    @Override
    public String toString() {
        return host;
    }
}

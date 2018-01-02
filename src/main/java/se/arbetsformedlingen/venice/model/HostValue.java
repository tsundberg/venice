package se.arbetsformedlingen.venice.model;

import java.util.Objects;

public class HostValue implements Comparable<HostValue> {
    private Host host;
    private long load;

    public HostValue(Host host, long load) {
        this.host = host;
        this.load = load;
    }

    public Host getHost() {
        return host;
    }

    public long getLoad() {
        return load;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostValue hostValue = (HostValue) o;
        return Objects.equals(host, hostValue.host) &&
                load == hostValue.load;
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, load);
    }

    @Override
    public int compareTo(HostValue o) {
        return host.getName().compareTo(o.host.getName());
    }

    @Override
    public String toString() {
        return "HostValue{" +
                "host=" + host +
                ", load=" + load +
                '}';
    }
}

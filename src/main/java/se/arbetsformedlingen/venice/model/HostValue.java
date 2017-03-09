package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Host;

public class HostValue implements Comparable<HostValue> {
    private Host host;
    private Long load;

    public HostValue(Host host, Long load) {
        this.host = host;
        this.load = load;
    }

    public Host getHost() {
        return host;
    }

    public Long getLoad() {
        return load;
    }

    @Override
    public int compareTo(HostValue o) {
        return host.getName().compareTo(o.host.getName());
    }
}

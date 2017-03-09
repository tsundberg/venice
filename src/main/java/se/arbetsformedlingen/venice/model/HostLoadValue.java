package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Host;

public class HostLoadValue implements Comparable<HostLoadValue> {
    private Host host;
    private Long load;

    public HostLoadValue(Host host, Long load) {
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
    public int compareTo(HostLoadValue o) {
        return host.getName().compareTo(o.host.getName());
    }
}

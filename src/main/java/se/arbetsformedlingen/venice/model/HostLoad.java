package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Host;

public class HostLoad implements Comparable<HostLoad> {
    private Host host;
    private Long load;

    public HostLoad(Host host, Long load) {
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
    public int compareTo(HostLoad o) {
        return host.getName().compareTo(o.host.getName());
    }
}

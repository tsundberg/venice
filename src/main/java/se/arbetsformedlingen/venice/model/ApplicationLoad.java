package se.arbetsformedlingen.venice.model;

import se.arbetsformedlingen.venice.common.Host;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ApplicationLoad implements Comparable<ApplicationLoad> {
    private Host host;
    private Long load;

    public ApplicationLoad(Host host, Long load) {
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
    public int compareTo(ApplicationLoad o) {
        return host.getName().compareTo(o.host.getName());
    }
}

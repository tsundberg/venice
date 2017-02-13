package se.arbetsformedlingen.venice.probe;

import java.util.Objects;

public class ProbeResponse {
    private Host host;
    private Application application;
    private Version version;
    private Status status;

    ProbeResponse(Application application, Host host, Status status, Version version) {
        this.application = application;
        this.host = host;
        this.status = status;
        this.version = version;
    }

    Host getHost() {
        return host;
    }

    Application getApplication() {
        return application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProbeResponse that = (ProbeResponse) o;
        return Objects.equals(host, that.host) &&
                Objects.equals(application, that.application) &&
                Objects.equals(version, that.version) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, application, version, status);
    }

    @Override
    public String toString() {
        return "ProbeResponse{" +
                "host=" + host +
                ", application=" + application +
                ", version=" + version +
                ", status=" + status +
                '}';
    }
}

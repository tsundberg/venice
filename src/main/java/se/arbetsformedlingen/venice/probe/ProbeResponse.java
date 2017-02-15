package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;
import se.arbetsformedlingen.venice.common.Status;
import se.arbetsformedlingen.venice.common.Version;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProbeResponse {
    private Host host;
    private Application application;
    private Version version;
    private Status status;
    private LocalDateTime checkTimestamp = LocalDateTime.now();

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

    public String getVersion() {
        return version.toString();
    }

    public String getStatus() {
        return status.toString();
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
                ", checkTimestamp=" + checkTimestamp +
                '}';
    }
}

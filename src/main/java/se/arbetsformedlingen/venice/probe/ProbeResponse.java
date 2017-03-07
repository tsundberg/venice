package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.*;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProbeResponse {
    private Server server;
    private Version version;
    private Status status;
    private LocalDateTime checkTimestamp = LocalDateTime.now();

    ProbeResponse(Server server, Status status, Version version) {
        this.server = server;
        this.status = status;
        this.version = version;
    }

    Server getServer() {
        return server;
    }

    Host getHost() {
        return server.getHost();
    }

    Application getApplication() {
        return server.getApplication();
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
        return Objects.equals(server, that.server) &&
                Objects.equals(version, that.version) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, version, status);
    }

    @Override
    public String toString() {
        return "ProbeResponse{" +
                "server=" + server +
                ", version=" + version +
                ", status=" + status +
                ", checkTimestamp=" + checkTimestamp +
                '}';
    }
}

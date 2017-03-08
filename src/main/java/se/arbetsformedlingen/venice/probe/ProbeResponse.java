package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.*;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProbeResponse {
    private ApplicationServer applicationServer;
    private Version version;
    private Status status;
    private LocalDateTime checkTimestamp = LocalDateTime.now();

    ProbeResponse(ApplicationServer applicationServer, Status status, Version version) {
        this.applicationServer = applicationServer;
        this.status = status;
        this.version = version;
    }

    ApplicationServer getApplicationServer() {
        return applicationServer;
    }

    Host getHost() {
        return applicationServer.getHost();
    }

    Application getApplication() {
        return applicationServer.getApplication();
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
        return Objects.equals(applicationServer, that.applicationServer) &&
                Objects.equals(version, that.version) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationServer, version, status);
    }

    @Override
    public String toString() {
        return "ProbeResponse{" +
                "server=" + applicationServer +
                ", version=" + version +
                ", status=" + status +
                ", checkTimestamp=" + checkTimestamp +
                '}';
    }
}

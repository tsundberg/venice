package se.arbetsformedlingen.venice.ci;

import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.BuildNumber;
import se.arbetsformedlingen.venice.model.Status;

import java.util.Objects;

public class BuildResponse {
    private final Application application;
    private final Status status;
    private BuildNumber buildNumber;

    BuildResponse(Application application, Status status) {
        this.application = application;
        this.status = status;
    }

    public String getName() {
        return application.getName();
    }

    public String getStatus() {
        return status.toString();
    }

    public BuildNumber getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(BuildNumber buildNumber) {
        this.buildNumber = buildNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildResponse that = (BuildResponse) o;
        return Objects.equals(application, that.application) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, status);
    }

    @Override
    public String toString() {
        return "BuildResponse{" +
                "application=" + application +
                ", status=" + status +
                '}';
    }
}

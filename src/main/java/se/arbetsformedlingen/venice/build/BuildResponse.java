package se.arbetsformedlingen.venice.build;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Status;

import java.util.Objects;

public class BuildResponse {
    private final Application application;
    private final Status status;

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
}

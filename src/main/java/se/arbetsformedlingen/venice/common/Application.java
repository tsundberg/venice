package se.arbetsformedlingen.venice.common;

import java.util.Objects;

public class Application {
    private String application;

    public Application(String application) {
        this.application = application;
    }

    public String getApplicationName() {
        return application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(application, that.application);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application);
    }

    @Override
    public String toString() {
        return "Application{" +
                "application='" + application + '\'' +
                '}';
    }
}

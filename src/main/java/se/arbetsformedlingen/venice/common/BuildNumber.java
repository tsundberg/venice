package se.arbetsformedlingen.venice.common;

import java.util.Objects;

public class BuildNumber {
    private Integer buildNumber;

    public BuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildNumber that = (BuildNumber) o;
        return Objects.equals(buildNumber, that.buildNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildNumber);
    }

    @Override
    public String toString() {
        return buildNumber.toString();
    }
}

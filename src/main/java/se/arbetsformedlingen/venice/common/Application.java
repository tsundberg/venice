package se.arbetsformedlingen.venice.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Application {
    private String name;
    private static Map<String, String> probeNames;

    public Application(String name) {
        this.name = name;

        addProbes();
    }

    private void addProbes() {
        if (probeNames == null) {
            probeNames = new HashMap<>();

            probeNames.put("gfr", "UgkForetagProbe");
            probeNames.put("geo", "UgkGeoProbe");
            probeNames.put("cpr", "CprProbe");
            probeNames.put("agselect", "MarknadsanalysProbe");
        }
    }

    String getProbeName() {
        return probeNames.get(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

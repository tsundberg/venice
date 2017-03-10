package se.arbetsformedlingen.venice.model;

public class Webservice {
    private String name;

    public Webservice(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

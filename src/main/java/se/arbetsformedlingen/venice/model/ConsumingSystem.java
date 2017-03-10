package se.arbetsformedlingen.venice.model;

public class ConsumingSystem {
    private String name;

    public ConsumingSystem(String name) {
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

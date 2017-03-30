package se.arbetsformedlingen.venice.configuration;

import se.arbetsformedlingen.venice.model.Application;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private Map<Application, String> queryStrings = new HashMap<>();

    public Configuration() {
        queryStrings.put(new Application("gfr"), "se.arbetsformedlingen.foretag*");
        queryStrings.put(new Application("geo"), "se.arbetsformedlingen.geo*");
        queryStrings.put(new Application("cpr"), "se.arbetsformedlingen.cpr*");
        queryStrings.put(new Application("agselect"), "se.arbetsformedlingen.gfr.ma*");
    }

    public String getApplicationLoadSearchString(Application application) {
        if (queryStrings.containsKey(application)) {
            return queryStrings.get(application);
        }

        throw new ConfigurationException("Application load search string is not defined for " + application);
    }
}

package se.arbetsformedlingen.venice.configuration;

import org.yaml.snakeyaml.Yaml;
import se.arbetsformedlingen.venice.model.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private Map configurations;

    public Configuration(String configFile) {
        load(configFile);
    }

    public String getApplicationLoadSearchString(Application application) {
        Map app = getApplicationConfiguration(application);
        String serverLoadString = (String) app.get("serverLoadString");

        if (serverLoadString != null) {
            return serverLoadString;
        }

        throw new ConfigurationException("Application load search string is not defined for " + application);
    }

    String getTpjAdminHost() {
        return "l7700759.wpa.ams.se";
    }

    private Map getApplicationConfiguration(Application application) {
        List applications = (List) configurations.get("applications");
        for (Object appObject : applications) {
            Map app = (Map) appObject;

            String appName = (String) app.get("name");
            Application candidate = new Application(appName);

            if (application.equals(candidate)) {
                return app;
            }
        }
        return new HashMap();
    }

    private void load(String configFile) {
        Yaml yaml = new Yaml();
        try {
            configurations = (Map) yaml.load(getInputStream(configFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream getInputStream(String configFile) throws FileNotFoundException {
        if (new File(configFile).exists()) {
            return new FileInputStream(configFile);
        } else {
            return getClass().getResourceAsStream("/configuration.yaml");
        }
    }
}

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

    public String getTpjAdminHost() {
        Map tpjAdmin = (Map) configurations.get("tpjadmin");
        return (String) tpjAdmin.get("host");
    }

    public Integer getTpjAdminPort() {
        Map tpjAdmin = (Map) configurations.get("tpjadmin");
        return (Integer) tpjAdmin.get("port");
    }

    public String getTpjAdminUri() {
        Map tpjAdmin =  getTpjAdmin();

        String uri = (String) tpjAdmin.get("uri");

        if (uri != null) {
            return uri;
        }

        throw new ConfigurationException("tpjadmin uri is not defined");
    }

    private Map getTpjAdmin() {
        Map tpjAdmin = (Map) configurations.get("tpjadmin");

        if (tpjAdmin != null) {
            return tpjAdmin;
        }

        throw new ConfigurationException("tpjadmin is not defined");
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
            if (configurations == null) {
                configurations = new HashMap();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputStream getInputStream(String config) throws FileNotFoundException {
        if (new File(config).exists()) {
            return new FileInputStream(config);
        } else {
            return getClass().getResourceAsStream("/configuration.yaml");
        }
    }
}

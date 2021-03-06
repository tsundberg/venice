package se.arbetsformedlingen.venice.configuration;

import org.yaml.snakeyaml.Yaml;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.Probe;

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

    public String getTpjAdminHost() {
        Map tpjAdmin = getTpjAdmin();

        String host = (String) tpjAdmin.get("host");

        if (host != null) {
            return host;
        }

        throw new ConfigurationException("tpjadmin host is not defined");
    }

    public Integer getTpjAdminPort() {
        Map tpjAdmin = getTpjAdmin();

        Integer port = (Integer) tpjAdmin.get("port");

        if (port != null) {
            return port;
        }

        throw new ConfigurationException("tpjadmin port is not defined");
    }

    public String getTpjAdminUri() {
        Map tpjAdmin = getTpjAdmin();

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

    public String getCiServerHost() {
        Map ciServer = getCiServer();

        String host = (String) ciServer.get("host");

        if (host != null) {
            return host;
        }

        throw new ConfigurationException("continuousIntegration host is not defined");
    }

    public Integer getCiServerPort() {
        Map ciServer = getCiServer();

        Integer port = (Integer) ciServer.get("port");

        if (port != null) {
            return port;
        }

        throw new ConfigurationException("continuousIntegration port is not defined");
    }

    private Map getCiServer() {
        Map continuousIntegration = (Map) configurations.get("continuousIntegration");

        if (continuousIntegration != null) {
            return continuousIntegration;
        }

        throw new ConfigurationException("continuousIntegration is not defined");
    }

    public Probe getProbe(String applicationName) {
        Map applicationConfig = getApplication(applicationName);

        String name = (String) applicationConfig.get("probe");

        if (name != null) {
            return new Probe(name);
        }

        throw new ConfigurationException("probe is not defined for " + applicationName);
    }

    private Map getApplication(String wantedApplication) {
        List applications = (List) configurations.get("applications");

        for (Object c: applications) {
            Map candidate = (Map) c;
            String name = (String) candidate.get("name");
            if (name != null && name.equals(wantedApplication)) {
                return candidate;
            }
        }

        throw new ConfigurationException("applications application is not defined for " + wantedApplication);
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

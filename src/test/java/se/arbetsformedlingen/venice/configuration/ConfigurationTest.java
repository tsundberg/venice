package se.arbetsformedlingen.venice.configuration;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.Probe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class ConfigurationTest {

    @Test
    public void missing_tpj_admin_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/empty-configuration.yaml");

        assertThatThrownBy(configuration::getTpjAdminUri)
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("tpjadmin is not defined");
    }

    @Test
    public void read_tpj_admin_host_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        String actual = configuration.getTpjAdminHost();

        assertThat(actual).isEqualTo("l7700759.wpa.ams.se");
    }

    @Test
    public void default_tpj_admin_host_from_config_file() {
        Configuration configuration = new Configuration("no file");

        String actual = configuration.getTpjAdminHost();

        assertThat(actual).isEqualTo("l7700759.wpa.ams.se");
    }

    @Test
    public void missing_tpj_admin_host_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/missing-nodes-configuration.yaml");

        assertThatThrownBy(configuration::getTpjAdminHost)
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("tpjadmin host is not defined");
    }

    @Test
    public void read_tpj_admin_port_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        Integer actual = configuration.getTpjAdminPort();

        assertThat(actual).isEqualTo(8180);
    }

    @Test
    public void default_tpj_admin_port_from_config_file() {
        Configuration configuration = new Configuration("no file");

        Integer actual = configuration.getTpjAdminPort();

        assertThat(actual).isEqualTo(8180);
    }

    @Test
    public void missing_tpj_admin_port_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/missing-nodes-configuration.yaml");

        assertThatThrownBy(configuration::getTpjAdminPort)
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("tpjadmin port is not defined");
    }

    @Test
    public void read_tpj_admin_uri_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        String actual = configuration.getTpjAdminUri();

        assertThat(actual).isEqualTo("/tpjadmin/rest/properties/v0/wildfly/instances/");
    }

    @Test
    public void default_tpj_admin_uri_from_config_file() {
        Configuration configuration = new Configuration("no file");

        String actual = configuration.getTpjAdminUri();

        assertThat(actual).isEqualTo("/tpjadmin/rest/properties/v0/wildfly/instances/");
    }

    @Test
    public void missing_tpj_admin_uri_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/missing-nodes-configuration.yaml");

        assertThatThrownBy(configuration::getTpjAdminUri)
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("tpjadmin uri is not defined");
    }

    @Test
    public void missing_continuous_integration_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/empty-configuration.yaml");

        assertThatThrownBy(configuration::getCiServerHost)
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("continuousIntegration is not defined");
    }

    @Test
    public void read_ci_server_host_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        String actual = configuration.getCiServerHost();

        assertThat(actual).isEqualTo("l7700676.ws.ams.se");
    }

    @Test
    public void default_ci_server_host_from_config_file() {
        Configuration configuration = new Configuration("no file");

        String actual = configuration.getCiServerHost();

        assertThat(actual).isEqualTo("l7700676.ws.ams.se");
    }

    @Test
    public void missing_ci_server_host_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/missing-nodes-configuration.yaml");

        assertThatThrownBy(configuration::getCiServerHost)
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("continuousIntegration host is not defined");
    }

    @Test
    public void read_ci_server_port_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        Integer actual = configuration.getCiServerPort();

        assertThat(actual).isEqualTo(8080);
    }

    @Test
    public void default_ci_server_port_from_config_file() {
        Configuration configuration = new Configuration("no file");

        Integer actual = configuration.getCiServerPort();

        assertThat(actual).isEqualTo(8080);
    }

    @Test
    public void missing_ci_server_port_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/missing-nodes-configuration.yaml");

        assertThatThrownBy(configuration::getCiServerPort)
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("continuousIntegration port is not defined");
    }

    @Test
    public void read_geo_probe_name_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        Probe actual = configuration.getProbe("geo");

        assertThat(actual).isEqualTo(new Probe("UgkGeoProbe"));
    }

    @Test
    public void default_geo_probe_name_from_config_file() {
        Configuration configuration = new Configuration("no file");

        Probe actual = configuration.getProbe("gfr");

        assertThat(actual).isEqualTo(new Probe("UgkForetagProbe"));
    }

    @Test
    public void missing_geo_probe_name_from_config_file() {
        Configuration configuration = new Configuration("build/resources/test/missing-nodes-configuration.yaml");

        assertThatThrownBy(() -> configuration.getProbe("geo"))
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("applications application is not defined for geo");

    }

}

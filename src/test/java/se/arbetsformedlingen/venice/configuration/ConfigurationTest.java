package se.arbetsformedlingen.venice.configuration;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class ConfigurationTest {

    @Test
    public void read_tpj_admin_host_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        String actual = configuration.getTpjAdminHost();

        assertThat(actual).isEqualTo("l7700759.wpa.ams.se");
    }

    @Test
    public void read_tpj_admin_port_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        Integer actual = configuration.getTpjAdminPort();

        assertThat(actual).isEqualTo(8180);
    }

    @Test
    public void read_tpj_admin_uri_from_config_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        String actual = configuration.getTpjAdminUri();

        assertThat(actual).isEqualTo("/tpjadmin/rest/properties/v0/wildfly/instances/");
    }

    @Test
    public void get_application_load_search_string_for_gfr() {
        Configuration configuration = new Configuration("no file");

        String expected = "se.arbetsformedlingen.foretag*";
        Application gfr = new Application("gfr");

        String actual = configuration.getApplicationLoadSearchString(gfr);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fail_getting_application_load_search_string_for_unknown_application() {
        Configuration configuration = new Configuration("no file");

        Application unknown = new Application("unknown");

        Throwable thrown = catchThrowable(() -> configuration.getApplicationLoadSearchString(unknown));

        assertThat(thrown).isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("Application load search string is not defined for unknown");
    }

}


package se.arbetsformedlingen.venice.configuration;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class ConfigurationTest {
    private Configuration configuration = new Configuration("no file");

    @Test
    public void read_config_from_file() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");

        String expected = "l7700759.wpa.ams.se";

        String actual = configuration.getTpjAdminHost();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void get_application_load_search_string_for_gfr() {
        String expected = "se.arbetsformedlingen.foretag*";
        Application gfr = new Application("gfr");

        String actual = configuration.getApplicationLoadSearchString(gfr);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void fail_getting_application_load_search_string_for_unknown_application() {
        Application unknown = new Application("unknown");

        Throwable thrown = catchThrowable(() -> configuration.getApplicationLoadSearchString(unknown));

        assertThat(thrown).isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("Application load search string is not defined for unknown");
    }

}


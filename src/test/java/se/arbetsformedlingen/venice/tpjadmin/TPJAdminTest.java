package se.arbetsformedlingen.venice.tpjadmin;

import org.junit.Test;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.model.Application;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TPJAdminTest {

    @Test
    public void get_applications() {
        Application expected = new Application("geo");

        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");
        TPJAdmin tpjAdmin = new TPJAdmin(configuration);

        List<Application> actual = tpjAdmin.getApplications();

        assertThat(actual).contains(expected);
    }

}


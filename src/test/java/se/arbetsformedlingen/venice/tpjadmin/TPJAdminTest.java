package se.arbetsformedlingen.venice.tpjadmin;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TPJAdminTest {

    @Test
    public void get_applications() {
        Application expected = new Application("geo");

        List<Application> actual = TPJAdmin.getApplications();

        assertThat(actual).contains(expected);
    }

}


package se.arbetsformedlingen.venice.tpjadmin;

import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TPJAdminIT {

    @Test
    @Ignore
    public void get_servers() throws Exception {
        ApplicationServer expected = new ApplicationServer(new Application("gfr"), new Environment("u1"), new Host("l7700649.u1.local"), new Port("8580"));

        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");
        TPJAdmin tpjAdmin = new TPJAdmin(configuration);

        List<ApplicationServer> actual = tpjAdmin.prepareApplicationServers();

        assertThat(actual).contains(expected);
    }
}

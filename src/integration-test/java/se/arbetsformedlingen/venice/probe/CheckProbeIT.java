package se.arbetsformedlingen.venice.probe;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CheckProbeIT {

    @Test
    public void check_gfr_u1() {
        Application application = new Application("gfr");
        Environment environment = new Environment("u1");
        Host host = new Host("L7700649.u1.local");
        Port port = new Port("8580");

        ApplicationServer applicationServer = new ApplicationServer(application, environment, host, port);

        CheckProbe probe = new CheckProbe(applicationServer);

        ProbeResponse actual = probe.get();

        assertThat(actual.getHost()).isEqualTo(host);
        assertThat(actual.getApplication()).isEqualTo(application);
    }

}

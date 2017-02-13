package se.arbetsformedlingen.venice.probe;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CheckProbeTest {

    @Test
    public void check_gfr_u1() {
        String hostName = "L7700649.u1.local";
        Host host = new Host(hostName);
        String applicationName = "gfr";
        Application application = new Application(applicationName);
        CheckProbe probe = new CheckProbe(host, application);

        ProbeResponse actual = probe.get();

        assertThat(actual.getHostName()).isEqualTo(hostName);
        assertThat(actual.getApplicationName()).isEqualTo(applicationName);
    }

}

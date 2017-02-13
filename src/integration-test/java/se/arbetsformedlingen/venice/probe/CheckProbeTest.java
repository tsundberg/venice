package se.arbetsformedlingen.venice.probe;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CheckProbeTest {

    @Test
    public void check_gfr_u1() {
        Host host = new Host("L7700649.u1.local");
        Application application = new Application("gfr");
        CheckProbe probe = new CheckProbe(host, application);

        ProbeResponse actual = probe.get();

        assertThat(actual.getHost()).isEqualTo(host);
        assertThat(actual.getApplication()).isEqualTo(application);
    }

}

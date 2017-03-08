package se.arbetsformedlingen.venice.probe;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.*;

import static org.assertj.core.api.Assertions.assertThat;


public class CheckProbeTest {
    @Test
    public void build_url() {
        Application application = new Application("gfr");
        Environment environment = new Environment("u1");
        Host host = new Host("L7700770");
        Port port = new Port("8580");
        ApplicationServer applicationServer = new ApplicationServer(application, environment, host, port);

        CheckProbe checkProbe = new CheckProbe(applicationServer);
        String actual = checkProbe.getUri();

        assertThat(actual).contains("http");
        assertThat(actual).contains("L7700770");
        assertThat(actual).contains("8580");
        assertThat(actual).contains("/jolokia/read/af-probe:probe");
    }
}

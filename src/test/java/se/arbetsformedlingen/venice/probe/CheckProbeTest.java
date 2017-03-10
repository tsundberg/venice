package se.arbetsformedlingen.venice.probe;

import org.junit.Before;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import static org.assertj.core.api.Assertions.assertThat;


public class CheckProbeTest {
    private ApplicationServer applicationServer;

    @Before
    public void setUp() {
        Application application = new Application("gfr");
        Environment environment = new Environment("u1");
        Host host = new Host("L7700770");
        Port port = new Port("8580");
        applicationServer = new ApplicationServer(application, environment, host, port);
    }

    @Test
    public void build_url() {
        CheckProbe checkProbe = new CheckProbe(applicationServer);
        String actual = checkProbe.getUri();

        assertThat(actual).contains("http");
        assertThat(actual).contains("L7700770");
        assertThat(actual).contains("8580");
        assertThat(actual).contains("/jolokia/read/af-probe:probe");
    }

    @Test
    public void verify_error_response() {
        Status status = new Status("Oops!");
        Version version = new Version("Unknown");
        ProbeResponse expected = new ProbeResponse(applicationServer, status, version);

        CheckProbe checkProbe = new CheckProbe(applicationServer);

        Exception exceptionSample = new Exception("Oops!");
        ProbeResponse actual = checkProbe.errorResponse(exceptionSample);

        assertThat(actual).isEqualTo(expected);
    }
}

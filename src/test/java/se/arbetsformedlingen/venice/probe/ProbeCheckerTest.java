package se.arbetsformedlingen.venice.probe;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProbeCheckerTest {

    @Test
    public void check_probe() throws Exception {
        Application application = new Application("gfr");
        Environment environment = new Environment("u1");
        Host host = new Host("L7700770");
        Port port = new Port("8580");
        ApplicationServer applicationServer = new ApplicationServer(application, environment, host, port);

        Status status = new Status("OK");
        Version version = new Version("4.6.470");

        ProbeResponse expected = new ProbeResponse(applicationServer, status, version);

        LatestProbeStatuses latestProbeStatuses = new LatestProbeStatuses();

        CheckProbe probe = prepareMock();

        ProbeChecker checker = new ProbeChecker(probe, latestProbeStatuses);
        checker.run();

        while (checker.isWorking()) {
            Thread.sleep(5);
        }

        ProbeResponse actual = latestProbeStatuses.getStatus(applicationServer);

        assertThat(actual).isEqualTo(expected);
    }

    private CheckProbe prepareMock() {
        CheckProbe checkProbe = mock(CheckProbe.class);

        Application application = new Application("gfr");
        Environment environment = new Environment("u1");
        Host host = new Host("L7700770");
        Port port = new Port("8580");
        ApplicationServer applicationServer = new ApplicationServer(application, environment, host, port);

        Status status = new Status("OK");
        Version version = new Version("4.6.470");
        ProbeResponse response = new ProbeResponse(applicationServer, status, version);

        when(checkProbe.get()).thenReturn(response);

        return checkProbe;
    }
}

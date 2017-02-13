package se.arbetsformedlingen.venice.probe;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProbeCheckerTest {

    @Test
    public void check_probe() throws Exception {
        Application application = new Application("gfr");
        Host host = new Host("L7700770");
        Status status = new Status("OK");
        Version version = new Version("4.6.470");
        ProbeResponse expected = new ProbeResponse(application, host, status, version);

        ProbeStatus probeStatus = new ProbeStatus();

        CheckProbe probe = prepareMock();

        ProbeChecker checker = new ProbeChecker(probe, probeStatus);
        checker.run();

        while (checker.isWorking()) {
            Thread.sleep(5);
        }

        ProbeResponse actual = probeStatus.getStatus(new Host("L7700770"));

        assertThat(actual).isEqualTo(expected);
    }

    private CheckProbe prepareMock() {
        CheckProbe checkProbe = mock(CheckProbe.class);

        Application application = new Application("gfr");
        Host host = new Host("L7700770");
        Status status = new Status("OK");
        Version version = new Version("4.6.470");
        ProbeResponse response = new ProbeResponse(application, host, status, version);

        when(checkProbe.get()).thenReturn(response);

        return checkProbe;
    }
}

package se.arbetsformedlingen.venice.probe;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LatestProbeStatusesTest {

    @Test
    public void never_return_null() {
        LatestProbeStatuses statuses = new LatestProbeStatuses();

        Application application = new Application("application name");
        Environment environment = new Environment("env");
        Host host = new Host("host name");
        Port port = new Port("8580");
        ApplicationServer applicationServer = new ApplicationServer(application, environment, host, port);

        ProbeResponse actual = statuses.getStatus(applicationServer);

        assertThat(actual.getApplicationServer()).isEqualTo(applicationServer);
        assertThat(actual.getStatus()).isEqualTo("Unknown");
        assertThat(actual.getVersion()).isEqualTo("Unknown");
    }
}

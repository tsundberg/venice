package se.arbetsformedlingen.venice.probe;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LatestProbeStatusesTest {

    @Test
    public void never_return_null(){
        LatestProbeStatuses statuses = new LatestProbeStatuses();

        ProbeResponse actual = statuses.getStatus(new Host("host name"), new Application("application name"));

        assertThat(actual.getHostName()).isEqualTo("host name");
        assertThat(actual.getApplicationName()).isEqualTo("application name");
        assertThat(actual.getStatus()).isEqualTo("Unknown");
        assertThat(actual.getVersion()).isEqualTo("Unknown");
    }
}

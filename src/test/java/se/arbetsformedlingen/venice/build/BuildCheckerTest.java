package se.arbetsformedlingen.venice.build;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Status;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuildCheckerTest {

    @Test
    public void check_probe() throws Exception {
        Application application = new Application("gfr");
        Status status = new Status("OK");
        BuildResponse expected = new BuildResponse(application, status);

        LatestBuildStatuses latestProbeStatuses = new LatestBuildStatuses();

        CheckBuild probe = prepareMock();

        BuildChecker checker = new BuildChecker(probe, latestProbeStatuses);
        checker.run();

        while (checker.isWorking()) {
            Thread.sleep(5);
        }

        List<BuildResponse> actual = latestProbeStatuses.getStatuses();

        assertThat(actual).containsExactlyInAnyOrder(expected);
    }

    private CheckBuild prepareMock() {
        CheckBuild checkProbe = mock(CheckBuild.class);

        Application application = new Application("gfr");
        Status status = new Status("OK");
        BuildResponse response = new BuildResponse(application, status);

        List<BuildResponse> responses = new LinkedList<>();
        responses.add(response);

        when(checkProbe.get()).thenReturn(responses);

        return checkProbe;
    }
}

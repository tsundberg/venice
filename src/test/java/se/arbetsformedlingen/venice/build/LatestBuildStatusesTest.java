package se.arbetsformedlingen.venice.build;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Status;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class LatestBuildStatusesTest {

    @Test
    public void get_a_known_job() {
        LatestBuildStatuses statuses = new LatestBuildStatuses();

        BuildResponse expected = new BuildResponse(new Application("gfr"), new Status("OK"));
        List<BuildResponse> responses = new LinkedList<>();
        responses.add(expected);

        statuses.addStatus(responses);

        List<BuildResponse> actual = statuses.getStatuses();

        assertThat(actual).containsExactlyInAnyOrder(expected);
    }
}

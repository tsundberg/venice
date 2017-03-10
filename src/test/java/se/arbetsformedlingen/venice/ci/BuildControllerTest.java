package se.arbetsformedlingen.venice.ci;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.BuildNumber;
import se.arbetsformedlingen.venice.model.Status;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BuildControllerTest {

    @Test
    public void probe_status_for_gfr() {
        LatestBuildStatuses statuses = new LatestBuildStatuses();

        List<BuildResponse> responses = new LinkedList<>();

        BuildResponse buildResponse = new BuildResponse(new Application("gfr"), new Status("OK"));
        buildResponse.setBuildNumber(new BuildNumber(11));
        responses.add(buildResponse);

        statuses.addStatus(responses);

        String actual = BuildController.getBuilds(null, null);

        JSONArray builds = new JSONArray(actual);
        assertThat(builds.length()).isEqualTo(1);

        JSONObject build = builds.getJSONObject(0);
        assertThat(build.getString("buildnumber")).isEqualTo("11");
        assertThat(build.getString("name")).isEqualTo("gfr");
        assertThat(build.getString("status")).isEqualTo("OK");
    }

}

package se.arbetsformedlingen.venice.ci;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BuildResponseParserTest {

    @Test
    public void parse_build_response() {
        BuildResponse expectedGfr = new BuildResponse(new Application("gfr"), new Status("OK"));
        BuildResponse expectedGeo = new BuildResponse(new Application("geo"), new Status("ERROR"));

        String sampleJson = getSampleJson();

        List<BuildResponse> actual = BuildResponseParser.parse(sampleJson);

        assertThat(actual).contains(expectedGfr);
        assertThat(actual).contains(expectedGeo);
    }

    private String getSampleJson() {
        return "{\"actions\":[{},{},{},{},{},{},{},{}],\"description\":\"\",\"displayName\":\"Masterdata\",\"displayNameOrNull\":null,\"name\":\"Masterdata\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/\",\"healthReport\":[{\"description\":\"Average health of 4 items\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with builds: 4 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with successful builds: 4 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with failed builds: 0 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with unstable builds: 0 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Projects enabled for building: 4 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100}],\"jobs\":[{\"name\":\"agselect\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/agselect/\",\"color\":\"blue\"},{\"name\":\"cpr\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/cpr/\",\"color\":\"blue\"},{\"name\":\"geo\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/geo/\",\"color\":\"red\"},{\"name\":\"gfr\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/\",\"color\":\"blue\"}],\"primaryView\":{\"name\":\"All\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/\"},\"views\":[{\"name\":\"All\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/\"}]}";
    }

}

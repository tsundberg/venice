package se.arbetsformedlingen.venice.ci;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.BuildNumber;
import se.arbetsformedlingen.venice.common.Status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BuildResponseParserTest {

    @Test
    public void parse_build_response() {
        BuildResponse expectedGfr = new BuildResponse(new Application("gfr"), new Status("OK"));
        BuildResponse expectedGeo = new BuildResponse(new Application("geo"), new Status("ERROR"));

        String sampleJson = getProjectSampleJson();

        List<BuildResponse> actual = BuildResponseParser.parse(sampleJson);

        assertThat(actual).contains(expectedGfr);
        assertThat(actual).contains(expectedGeo);
    }

    @Test
    public void add_build_number() {
        BuildResponse buildResponse = new BuildResponse(new Application("gfr"), new Status("OK"));

        BuildResponseParser.addBuildNumber(buildResponse, getJobSampleJson());

        assertThat(buildResponse.getBuildNumber()).isEqualTo(new BuildNumber(565));
    }

    private String getProjectSampleJson() {
        return "{\"actions\":[{},{},{},{},{},{},{},{}],\"description\":\"\",\"displayName\":\"Masterdata\",\"displayNameOrNull\":null,\"name\":\"Masterdata\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/\",\"healthReport\":[{\"description\":\"Average health of 4 items\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with builds: 4 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with successful builds: 4 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with failed builds: 0 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Jobs with unstable builds: 0 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100},{\"description\":\"Projects enabled for building: 4 of 4\",\"iconClassName\":\"icon-health-80plus\",\"iconUrl\":\"health-80plus.png\",\"score\":100}],\"jobs\":[{\"name\":\"agselect\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/agselect/\",\"color\":\"blue\"},{\"name\":\"cpr\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/cpr/\",\"color\":\"blue\"},{\"name\":\"geo\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/geo/\",\"color\":\"red\"},{\"name\":\"gfr\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/\",\"color\":\"blue\"}],\"primaryView\":{\"name\":\"All\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/\"},\"views\":[{\"name\":\"All\",\"url\":\"http://jenkins-master1.ams.se/job/Masterdata/\"}]}";
    }

    private String getJobSampleJson() {
        return "{\n" +
                "  \"actions\" : [\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \"oneClickDeployPossible\" : false,\n" +
                "      \"oneClickDeployReady\" : true,\n" +
                "      \"oneClickDeployValid\" : false\n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    null,\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    }\n" +
                "  ],\n" +
                "  \"description\" : \"\",\n" +
                "  \"displayName\" : \"gfr\",\n" +
                "  \"displayNameOrNull\" : null,\n" +
                "  \"name\" : \"gfr\",\n" +
                "  \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/\",\n" +
                "  \"buildable\" : true,\n" +
                "  \"builds\" : [\n" +
                "    {\n" +
                "      \"number\" : 565,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/565/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 564,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/564/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 563,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/563/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 562,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/562/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 561,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/561/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 560,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/560/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 559,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/559/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 558,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/558/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 557,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/557/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 556,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/556/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 555,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/555/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 554,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/554/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 553,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/553/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 552,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/552/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 551,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/551/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 550,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/550/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 549,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/549/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 548,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/548/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 547,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/547/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 546,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/546/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 545,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/545/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 544,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/544/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 543,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/543/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 542,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/542/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 541,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/541/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 540,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/540/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 539,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/539/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 538,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/538/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 537,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/537/\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"number\" : 536,\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/536/\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"color\" : \"blue\",\n" +
                "  \"firstBuild\" : {\n" +
                "    \"number\" : 536,\n" +
                "    \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/536/\"\n" +
                "  },\n" +
                "  \"healthReport\" : [\n" +
                "    {\n" +
                "      \"description\" : \"Build stability: 1 out of the last 5 builds failed.\",\n" +
                "      \"iconClassName\" : \"icon-health-60to79\",\n" +
                "      \"iconUrl\" : \"health-60to79.png\",\n" +
                "      \"score\" : 80\n" +
                "    },\n" +
                "    {\n" +
                "      \"description\" : \"Test Result: 0 tests failing out of a total of 275 tests.\",\n" +
                "      \"iconClassName\" : \"icon-health-80plus\",\n" +
                "      \"iconUrl\" : \"health-80plus.png\",\n" +
                "      \"score\" : 100\n" +
                "    }\n" +
                "  ],\n" +
                "  \"inQueue\" : false,\n" +
                "  \"keepDependencies\" : false,\n" +
                "  \"lastBuild\" : {\n" +
                "    \"number\" : 565,\n" +
                "    \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/565/\"\n" +
                "  },\n" +
                "  \"lastCompletedBuild\" : {\n" +
                "    \"number\" : 565,\n" +
                "    \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/565/\"\n" +
                "  },\n" +
                "  \"lastFailedBuild\" : {\n" +
                "    \"number\" : 562,\n" +
                "    \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/562/\"\n" +
                "  },\n" +
                "  \"lastStableBuild\" : {\n" +
                "    \"number\" : 565,\n" +
                "    \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/565/\"\n" +
                "  },\n" +
                "  \"lastSuccessfulBuild\" : {\n" +
                "    \"number\" : 565,\n" +
                "    \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/565/\"\n" +
                "  },\n" +
                "  \"lastUnstableBuild\" : null,\n" +
                "  \"lastUnsuccessfulBuild\" : {\n" +
                "    \"number\" : 562,\n" +
                "    \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/562/\"\n" +
                "  },\n" +
                "  \"nextBuildNumber\" : 566,\n" +
                "  \"property\" : [\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \"instance\" : null\n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    }\n" +
                "  ],\n" +
                "  \"queueItem\" : null,\n" +
                "  \"concurrentBuild\" : false,\n" +
                "  \"downstreamProjects\" : [\n" +
                "    \n" +
                "  ],\n" +
                "  \"scm\" : {\n" +
                "    \n" +
                "  },\n" +
                "  \"upstreamProjects\" : [\n" +
                "    \n" +
                "  ],\n" +
                "  \"modules\" : [\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:ars-remedy\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$ars-remedy/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"ars-remedy\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:desktopapps\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$desktopapps/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"desktopapps\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-root\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-config\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-config/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-config\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-desktopapps\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-desktopapps/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"foretag-desktopapps\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-ear\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-ear/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-ear\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-ejb\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-ejb/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"foretag-ejb\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-external-bisnode\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-external-bisnode/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-external-bisnode\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-jms\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-jms/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-jms\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-logging\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-logging/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"foretag-logging\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-persistence\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-persistence/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-persistence\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-probe\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-probe/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-probe\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-service\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-service/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-service\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-v2-api-ws\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-v2-api-ws/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-v2-api-ws\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-v3-api-ws\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-v3-api-ws/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-v3-api-ws\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-v4-api-ws\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-v4-api-ws/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-v4-api-ws\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-v5-api-ws\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-v5-api-ws/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-v5-api-ws\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-v6-api-ws\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-v6-api-ws/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-v6-api-ws\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-war\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-war/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"foretag-war\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-web\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-web/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-web\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:foretag-webtng\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$foretag-webtng/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"foretag-webtng\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:gfr-ear\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$gfr-ear/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"gfr-ear\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:kreditkontroll\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$kreditkontroll/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"kreditkontroll\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:kreditkontroll-batch\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$kreditkontroll-batch/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"kreditkontroll-batch\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:load-test\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$load-test/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"load-test\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:smoke-test\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$smoke-test/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"smoke-test\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:test\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$test/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"test\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.gfr:ws-client\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.gfr$ws-client/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"ws-client\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.tpjavaee:foretag_w-rpm\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.tpjavaee$foretag_w-rpm/\",\n" +
                "      \"color\" : \"blue\",\n" +
                "      \"displayName\" : \"Foretag::RPM\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\" : \"se.arbetsformedlingen.tpjavaee:gfr_w.rpm\",\n" +
                "      \"url\" : \"http://jenkins-master1.ams.se/job/Masterdata/job/gfr/se.arbetsformedlingen.tpjavaee$gfr_w.rpm/\",\n" +
                "      \"color\" : \"notbuilt\",\n" +
                "      \"displayName\" : \"Foretag::RPM\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }


}

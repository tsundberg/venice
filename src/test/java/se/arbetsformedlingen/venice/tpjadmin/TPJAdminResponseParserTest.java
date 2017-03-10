package se.arbetsformedlingen.venice.tpjadmin;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TPJAdminResponseParserTest {

    @Test
    public void parse_response() {
        Application application = new Application("geo");
        Environment environment = new Environment("PROD");
        Host host1 = new Host("l7700746.wpa.ams.se");
        Host host2 = new Host("l7700747.wpa.ams.se");
        Host host3 = new Host("l7700770.wpa.ams.se");
        Port port = new Port("8180");

        ApplicationServer geo1 = new ApplicationServer(application, environment, host1, port);
        ApplicationServer geo2 = new ApplicationServer(application, environment, host2, port);
        ApplicationServer geo3 = new ApplicationServer(application, environment, host3, port);

        String jsonSample = "{\"l7700770.wpa.ams.se\":1, \"l7700747.wpa.ams.se\":1, \"l7700746.wpa.ams.se\":1}";

        List<ApplicationServer> actual = TPJAdminResponseParser.parse(application, environment, jsonSample);

        assertThat(actual).containsExactlyInAnyOrder(geo1, geo2, geo3);
    }

    @Test
    public void skip_parsing_response_with_broken_instance_number() {
        Application application = new Application("geo");
        Environment environment = new Environment("PROD");
        Host host1 = new Host("l7700746.wpa.ams.se");
        Host host3 = new Host("l7700770.wpa.ams.se");
        Port port = new Port("8180");

        ApplicationServer geo1 = new ApplicationServer(application, environment, host1, port);
        ApplicationServer geo3 = new ApplicationServer(application, environment, host3, port);

        String instanceNumber = "broken instance number";
        String jsonSample = "{\"l7700770.wpa.ams.se\":1, \"l7700747.wpa.ams.se\": " + instanceNumber + ", \"l7700746.wpa.ams.se\":1}";

        List<ApplicationServer> actual = TPJAdminResponseParser.parse(application, environment, jsonSample);

        assertThat(actual).containsExactlyInAnyOrder(geo1, geo3);
    }

}

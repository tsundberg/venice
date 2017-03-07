package se.arbetsformedlingen.venice.tpjadmin;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.*;

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

        Server geo1 = new Server(application, environment, host1, port);
        Server geo2 = new Server(application, environment, host2, port);
        Server geo3 = new Server(application, environment, host3, port);

        String jsonSample = "{\"l7700770.wpa.ams.se\":1, \"l7700747.wpa.ams.se\":1, \"l7700746.wpa.ams.se\":1}";

        List<Server> actual = TPJAdminResponseParser.parse(application, environment, jsonSample);

        assertThat(actual).containsExactlyInAnyOrder(geo1, geo2, geo3);
    }

}

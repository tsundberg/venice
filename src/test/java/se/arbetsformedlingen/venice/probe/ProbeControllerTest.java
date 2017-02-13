package se.arbetsformedlingen.venice.probe;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;
import se.arbetsformedlingen.venice.probe.ProbeController;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ProbeControllerTest {

    @Test
    public void probe_status_for_gfr() {
        String expected = "[" +
                "{\"name\":\"GFR\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}," +
                "{\"name\":\"GEO\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}," +
                "{\"name\":\"CPR\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}," +
                "{\"name\":\"AGSelect\",\"environments\":[{\"name\":\"production\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700770\":{\"status\":\"offline\",\"version\":\"4.6.401\"}}},{\"name\":\"t2\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"},\"L7700747\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"t1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"i1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}},{\"name\":\"u1\",\"servers\":{\"L7700746\":{\"status\":\"online\",\"version\":\"4.6.401\"}}}]}" +
                "]";

        String actual = ProbeController.getStatus(null, null);

        JsonParser parser = new JsonParser();
        JsonElement parsedExpected = parser.parse(expected);
        JsonElement parsedActual = parser.parse(actual);
        assertThat(parsedActual).isEqualTo(parsedExpected);
    }

}

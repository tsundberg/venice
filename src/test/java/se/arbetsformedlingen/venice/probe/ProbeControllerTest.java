package se.arbetsformedlingen.venice.probe;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import se.arbetsformedlingen.venice.common.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProbeControllerTest {

    @Test
    public void probe_status_for_gfr() {
        ProbeController.forceStatuses(new LatestProbeStatuses());
        ProbeController.forceServers(getServers());

        String actual = ProbeController.getStatus(null, null);

        verifyJsonStructure(actual);
    }

    private void verifyJsonStructure(String actual) {
        JSONArray array = new JSONArray(actual);
        assertThat(array.length()).isEqualTo(2);

        JSONObject jsonObj = array.getJSONObject(0);
        assertThat(jsonObj.getString("name")).isEqualTo("agselect");

        JSONArray environments = jsonObj.getJSONArray("environments");
        assertThat(environments.length()).isEqualTo(2);

        JSONObject productionServers = environments.getJSONObject(0);
        assertThat(productionServers.getString("name")).isEqualTo("PROD");

        JSONObject servers = productionServers.getJSONObject("servers");
        assertThat(servers.length()).isEqualTo(3);

        String firstServerName = servers.names().getString(0);
        JSONObject firstServer = servers.getJSONObject(firstServerName);
        assertThat(firstServer.has("version")).isTrue();
        assertThat(firstServer.has("status")).isTrue();
    }

    private List<ApplicationServer> getServers() {
        Application gfr = new Application("gfr");
        Application agselect = new Application("agselect");

        Environment prod = new Environment("PROD");
        Environment t2 = new Environment("t2");

        Port port = new Port("8180");

        List<ApplicationServer> applicationServers = new LinkedList<>();

        ApplicationServer gfr8 = new ApplicationServer(gfr, prod, new Host("l7700746.wpa.ams.se"), port);
        applicationServers.add(gfr8);

        ApplicationServer agselect5 = new ApplicationServer(agselect, t2, new Host("l7700836.ata.ams.se"), port);
        ApplicationServer agselect6 = new ApplicationServer(agselect, prod, new Host("l7700843.wpa.ams.se"), port);
        ApplicationServer agselect7 = new ApplicationServer(agselect, prod, new Host("l7700842.wpa.ams.se"), port);
        ApplicationServer agselect8 = new ApplicationServer(agselect, prod, new Host("l7700841.wpa.ams.se"), port);
        applicationServers.add(agselect5);
        applicationServers.add(agselect6);
        applicationServers.add(agselect7);
        applicationServers.add(agselect8);

        Collections.shuffle(applicationServers);
        Collections.sort(applicationServers);

        return applicationServers;
    }
}

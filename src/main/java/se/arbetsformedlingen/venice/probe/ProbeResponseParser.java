package se.arbetsformedlingen.venice.probe;

import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.*;

class ProbeResponseParser {
    private ProbeResponseParser() {
    }

    static ProbeResponse parse(Server server, String json) {
        JSONObject jsonObject = new JSONObject(json);

        JSONObject probeStatus = jsonObject.getJSONObject("value").getJSONObject("ProbeStatus");

        String applicationName = probeStatus.getString("application");
        Application application = new Application(applicationName);

        String fdqn = probeStatus.getString("fqdn");
        Host host = new Host(fdqn);

        String applicationStatus = probeStatus.getString("status");
        Status status = new Status(applicationStatus);

        String applicationVersion = probeStatus.getString("applicationVersion");
        Version version = new Version(applicationVersion);

        return new ProbeResponse(server, status, version);
    }
}

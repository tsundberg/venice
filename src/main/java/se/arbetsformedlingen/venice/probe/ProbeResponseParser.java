package se.arbetsformedlingen.venice.probe;

import org.json.JSONObject;
import se.arbetsformedlingen.venice.model.*;

class ProbeResponseParser {
    static ProbeResponse parse(ApplicationServer applicationServer, String json) {
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

        return new ProbeResponse(applicationServer, status, version);
    }
}

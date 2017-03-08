package se.arbetsformedlingen.venice.tpjadmin;

import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.*;

import java.util.LinkedList;
import java.util.List;

class TPJAdminResponseParser {

    static List<ApplicationServer> parse(Application application, Environment environment, String json) {
        List<ApplicationServer> applicationServers = new LinkedList<>();

        JSONObject jsonObject = new JSONObject(json);

        for (String key : jsonObject.keySet()) {
            Host host = new Host(key);

            Object portCandidate = jsonObject.get(key);

            if (portCandidate == null || !(portCandidate instanceof Integer)) {
                continue;
            }

            String portNumber = portCandidate.toString();
            portNumber = "8" + portNumber + "80";

            Port port = new Port(portNumber);

            ApplicationServer applicationServer = new ApplicationServer(application, environment, host, port);
            applicationServers.add(applicationServer);
        }

        return applicationServers;
    }
}

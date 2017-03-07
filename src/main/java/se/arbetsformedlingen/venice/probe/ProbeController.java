package se.arbetsformedlingen.venice.probe;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.Server;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;
import spark.Request;
import spark.Response;

import java.util.List;

public class ProbeController {

    public static String getStatus(Request request, Response response) {
        LatestProbeStatuses statuses = new LatestProbeStatuses();

        consoleLog(statuses);

        List<Server> servers = TPJAdmin.getServers();

        JsonRespnseBuilder jsonRespnseBuilder = new JsonRespnseBuilder(statuses);

        return jsonRespnseBuilder.build(servers);
    }

    private static void consoleLog(LatestProbeStatuses statuses) {
        System.out.println();

        List<Server> servers = TPJAdmin.getServers();
        for (Server server : servers) {
            ProbeResponse resp = statuses.getStatus(server);
            System.out.println(resp);
        }

        System.out.println();
    }
}

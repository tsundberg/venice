package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Server;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;
import spark.Request;
import spark.Response;

import java.util.List;

public class ProbeController {
    private static LatestProbeStatuses statuses = new LatestProbeStatuses();
    private static List<Server> servers = TPJAdmin.getServers();

    public static String getStatus(Request request, Response response) {
        consoleLog(statuses);

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

    static void forceStatuses(LatestProbeStatuses statuses) {
        ProbeController.statuses = statuses;
    }
    static void forceServers(List<Server> servers) {
        ProbeController.servers = servers;
    }
}

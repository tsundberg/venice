package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.ApplicationServer;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;
import spark.Request;
import spark.Response;

import java.util.List;

public class ProbeController {
    private static LatestProbeStatuses statuses = new LatestProbeStatuses();
    private static List<ApplicationServer> applicationServers = TPJAdmin.getApplicationServers();

    public static String getStatus(Request request, Response response) {
        consoleLog(statuses);

        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(statuses);
        return jsonResponseBuilder.build(applicationServers);
    }

    private static void consoleLog(LatestProbeStatuses statuses) {
        System.out.println();

        List<ApplicationServer> applicationServers = TPJAdmin.getApplicationServers();
        for (ApplicationServer applicationServer : applicationServers) {
            ProbeResponse resp = statuses.getStatus(applicationServer);
            System.out.println(resp);
        }

        System.out.println();
    }

    static void forceStatuses(LatestProbeStatuses statuses) {
        ProbeController.statuses = statuses;
    }
    static void forceServers(List<ApplicationServer> applicationServers) {
        ProbeController.applicationServers = applicationServers;
    }
}

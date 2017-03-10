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
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(statuses);
        return jsonResponseBuilder.build(applicationServers);
    }

    static void forceStatuses(LatestProbeStatuses statuses) {
        ProbeController.statuses = statuses;
    }
    static void forceServers(List<ApplicationServer> applicationServers) {
        ProbeController.applicationServers = applicationServers;
    }
}

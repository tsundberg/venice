package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.model.ApplicationServer;
import spark.Request;
import spark.Response;

import java.util.List;

public class ProbeController {
    private static LatestProbeStatuses statuses = new LatestProbeStatuses();
    private static List<ApplicationServer> applicationServers;

    public static String getStatus(Request request, Response response) {
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(statuses);
        return jsonResponseBuilder.build(applicationServers);
    }

    static void forceStatuses(LatestProbeStatuses statuses) {
        ProbeController.statuses = statuses;
    }

    public static void setServers(List<ApplicationServer> applicationServers) {
        ProbeController.applicationServers = applicationServers;
    }
}

package se.arbetsformedlingen.venice.probe;

import se.arbetsformedlingen.venice.common.Server;

import java.util.List;

public class JsonRespnseBuilder {
    private LatestProbeStatuses statuses;

    public JsonRespnseBuilder(LatestProbeStatuses statuses) {
        this.statuses = statuses;
    }

    public String build(List<Server> servers) {
        return "";
    }
}

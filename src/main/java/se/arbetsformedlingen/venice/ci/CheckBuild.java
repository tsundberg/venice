package se.arbetsformedlingen.venice.ci;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Status;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;

import java.util.LinkedList;
import java.util.List;

public class CheckBuild implements java.util.function.Supplier<List<BuildResponse>> {
    private static final String MASTERDATA_URL = "http://l7700676.ws.ams.se:8080/job/Masterdata/api/json";

    @Override
    public List<BuildResponse> get() {
        Executor executor = Executor.newInstance();

        String result;
        try {
            result = executor.execute(Request.Get(MASTERDATA_URL))
                    .returnContent()
                    .asString();

            return BuildResponseParser.parse(result);
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    List<BuildResponse> errorResponse(Exception e) {
        List<BuildResponse> responses = new LinkedList<>();

        for (Application application : TPJAdmin.getApplications()) {
            Status status = new Status(e.getMessage());
            BuildResponse error = new BuildResponse(application, status);

            responses.add(error);
        }

        return responses;
    }
}

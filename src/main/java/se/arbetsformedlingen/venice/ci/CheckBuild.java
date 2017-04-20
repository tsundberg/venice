package se.arbetsformedlingen.venice.ci;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.Status;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CheckBuild implements java.util.function.Supplier<List<BuildResponse>> {
    private static final String MASTERDATA_URL = "http://l7700676.ws.ams.se:8080/job/Masterdata/api/json";
    private static final String JOB_URL_HEAD = "http://l7700676.ws.ams.se:8080/job/Masterdata/job/";
    private static final String JOB_URL_TAIL = "/api/json";
    private TPJAdmin tpjAdmin;

    CheckBuild(TPJAdmin tpjAdmin) {
        this.tpjAdmin = tpjAdmin;
    }

    @Override
    public List<BuildResponse> get() {
        Executor executor = Executor.newInstance();

        String result;
        try {
            result = executor.execute(Request.Get(MASTERDATA_URL))
                    .returnContent()
                    .asString();

            List<BuildResponse> builds = BuildResponseParser.parse(result);

            for (BuildResponse build : builds) {
                addBuildNumber(build);
            }

            return builds;
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    private void addBuildNumber(BuildResponse build) throws IOException {
        Executor executor = Executor.newInstance();

        String url = JOB_URL_HEAD + build.getName() + JOB_URL_TAIL;

        String result;
        result = executor.execute(Request.Get(url))
                .returnContent()
                .asString();

        BuildResponseParser.addBuildNumber(build, result);
    }

    List<BuildResponse> errorResponse(Exception e) {
        List<BuildResponse> responses = new LinkedList<>();

        for (Application application : tpjAdmin.getApplications()) {
            Status status = new Status(e.getMessage());
            BuildResponse error = new BuildResponse(application, status);

            responses.add(error);
        }

        return responses;
    }
}

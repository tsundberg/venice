package se.arbetsformedlingen.venice.ci;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.Status;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CheckBuild implements java.util.function.Supplier<List<BuildResponse>> {
    private TPJAdmin tpjAdmin;
    private Configuration configuration;

    CheckBuild(TPJAdmin tpjAdmin, Configuration configuration) {
        this.tpjAdmin = tpjAdmin;
        this.configuration = configuration;
    }

    @Override
    public List<BuildResponse> get() {
        Executor executor = Executor.newInstance();

        String result;
        try {
            String host = configuration.getCiServerHost();
            Integer port = configuration.getCiServerPort();

            // todo config
            String projectUri = "http://" + host + ":" + port + "/job/Masterdata/api/json";
            result = executor.execute(Request.Get(projectUri))
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

        String host = configuration.getCiServerHost();
        Integer port = configuration.getCiServerPort();
        String jobUrlHead = "http://" + host + ":" + port + "/job/Masterdata/job/";
        String jobUrlTail = "/api/json";
        String url = jobUrlHead + build.getName() + jobUrlTail;

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

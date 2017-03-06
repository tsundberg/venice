package se.arbetsformedlingen.venice.ci;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Status;

import java.util.LinkedList;
import java.util.List;

public class CheckBuild implements java.util.function.Supplier<List<BuildResponse>> {

    @Override
    public List<BuildResponse> get() {
        String uri = getUri();
        Executor executor = Executor.newInstance();

        String result;
        try {
            result = executor.execute(Request.Get(uri))
                    .returnContent()
                    .asString();

            return BuildResponseParser.parse(result);
        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    private String getUri() {
        return "http://l7700676.ws.ams.se:8080/job/Masterdata/api/json";
    }

    private List<BuildResponse> errorResponse(Exception e) {
        List<BuildResponse> responses = new LinkedList<>();

        Application application = new Application("gfr");
        Status status = new Status(e.getMessage());
        BuildResponse error = new BuildResponse(application, status);

        responses.add(error);

        return responses;
    }
}

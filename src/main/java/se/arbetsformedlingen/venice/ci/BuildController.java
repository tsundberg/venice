package se.arbetsformedlingen.venice.ci;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.List;

public class BuildController {
    public static String getBuilds(Request request, Response response) {
        LatestBuildStatuses statuses = new LatestBuildStatuses();

        BuildController.JsonResponseBuilder resBuilder = new BuildController.JsonResponseBuilder(statuses);

        return resBuilder.buildJobList(statuses.getStatuses()).toString();
    }

    private static class JsonResponseBuilder {
        private LatestBuildStatuses statuses;

        private JsonResponseBuilder(LatestBuildStatuses statuses) {
            this.statuses = statuses;
        }

        private JSONObject buildJobObject(BuildResponse job) {
            JSONObject obj = new JSONObject();
            obj.put("name", job.getName());
            obj.put("status", job.getStatus());
            obj.put("buildnumber", job.getBuildNumber());

            return obj;
        }

        private JSONArray buildJobList(List<BuildResponse> jobs) {
            JSONArray arr = new JSONArray();
            for (BuildResponse job : jobs) {
                arr.put(buildJobObject(job));
            }

            return arr;
        }
    }


    private static void consoleLog(List<BuildResponse> jobs) {
        System.out.println();

        for (BuildResponse job : jobs) {
            System.out.println(job.getName() + ": " + job.getStatus());
        }

        System.out.println();
    }
}

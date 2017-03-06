package se.arbetsformedlingen.venice.build;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Status;

import java.util.LinkedList;
import java.util.List;

class BuildResponseParser {
    static List<BuildResponse> parse(String json) {
        List<BuildResponse> builds = new LinkedList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jobs = jsonObject.getJSONArray("jobs");

        for (Object o : jobs) {
            JSONObject job = (JSONObject) o;

            String applicationName = job.getString("name");
            String buildColor = job.getString("color");

            Application application = new Application(applicationName);
            Status status = new Status("OK");

            if (!buildColor.equals("blue")) {
                status = new Status("ERROR");
            }

            builds.add(new BuildResponse(application, status));
        }

        return builds;
    }
}

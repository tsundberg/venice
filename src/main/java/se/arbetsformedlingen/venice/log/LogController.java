package se.arbetsformedlingen.venice.log;

import org.json.JSONObject;
import spark.Request;
import spark.Response;

public class LogController {
    public static String getLogs(Request request, Response response) {
        request.params(":application");
        request.params(":logType");

        return notYet();
    }

    private static String notYet() {
        JSONObject response = new JSONObject();
        response.put("status", "Hold your horses, I'm not done yet.");

        return response.toString();
    }
}

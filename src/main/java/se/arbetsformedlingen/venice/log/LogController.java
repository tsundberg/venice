package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.LogType;
import spark.Request;
import spark.Response;

public class LogController {
    public static String getLogs(Request request, Response response) {
        LatestLogs latestLogs = new LatestLogs();

        Application application = getApplication(request);
        LogType logType = getLogType(request);

        LogResponse log = latestLogs.getLog(application, logType);

        JsonResponseBuilder responseBuilder = new JsonResponseBuilder();

        return responseBuilder.build(log);
    }

    private static Application getApplication(Request request) {
        return new Application(request.params(":application"));
    }

    private static LogType getLogType(Request request) {
        return new LogType(request.params(":logType"));
    }
}

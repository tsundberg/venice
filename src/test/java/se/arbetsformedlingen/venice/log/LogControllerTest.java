package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeries;
import se.arbetsformedlingen.venice.model.TimeSeriesValue;
import spark.Request;
import spark.RequestStub;

import static org.assertj.core.api.Assertions.assertThat;

public class LogControllerTest {

    @Test
    public void get_gfr_exceptions() {
        prepareLatestLog();
        Request request = prepareGfrExceptionsRequest();

        String actual = LogController.getLogs(request, null);

        JSONObject logResponse = new JSONObject(actual);
        assertThat(logResponse.keySet()).containsOnly("logType", "application", "timeSeries");

        JSONArray timeSeries = logResponse.getJSONArray("timeSeries");
        assertThat(timeSeries.length()).isEqualTo(24);
    }

    private Request prepareGfrExceptionsRequest() {
        return new RequestStub.RequestBuilder()
                .params(":application", "gfr")
                .params(":logType", "exception")
                .build();
    }

    private void prepareLatestLog() {
        LatestLogs latestLog = new LatestLogs();

        Application application = new Application("gfr");
        LogType logType = new LogType("exception");

        TimeSeries timeSeries = new TimeSeries(
                new TimeSeriesValue(22, 3), new TimeSeriesValue(1, 7), new TimeSeriesValue(13, 41), new TimeSeriesValue(2, 3),
                new TimeSeriesValue(8, 17), new TimeSeriesValue(4, 43), new TimeSeriesValue(3, 17), new TimeSeriesValue(5, 42),
                new TimeSeriesValue(0, 4), new TimeSeriesValue(19, 33), new TimeSeriesValue(6, 28), new TimeSeriesValue(7, 1),
                new TimeSeriesValue(9, 18), new TimeSeriesValue(11, 11), new TimeSeriesValue(12, 13), new TimeSeriesValue(14, 25),
                new TimeSeriesValue(15, 15), new TimeSeriesValue(16, 22), new TimeSeriesValue(10, 32), new TimeSeriesValue(18, 22),
                new TimeSeriesValue(17, 3), new TimeSeriesValue(21, 6), new TimeSeriesValue(20, 12), new TimeSeriesValue(23, 4)
        );

        LogResponse gfrExceptionLog = new LogResponse(application, logType, timeSeries);

        latestLog.addLog(gfrExceptionLog);
    }
}

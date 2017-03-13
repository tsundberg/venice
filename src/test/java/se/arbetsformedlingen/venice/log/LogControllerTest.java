package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.ExceptionsPerTime;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeriesValue;
import spark.Request;
import spark.RequestStub;

import java.util.LinkedList;
import java.util.List;

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

        List<TimeSeriesValue> timeSeriesValues = new LinkedList<>();
        timeSeriesValues.add(new TimeSeriesValue(22, 3));
        timeSeriesValues.add(new TimeSeriesValue(1, 7));
        timeSeriesValues.add(new TimeSeriesValue(13, 41));
        timeSeriesValues.add(new TimeSeriesValue(2, 3));
        timeSeriesValues.add(new TimeSeriesValue(8, 17));
        timeSeriesValues.add(new TimeSeriesValue(4, 43));
        timeSeriesValues.add(new TimeSeriesValue(3, 17));
        timeSeriesValues.add(new TimeSeriesValue(5, 42));
        timeSeriesValues.add(new TimeSeriesValue(0, 4));
        timeSeriesValues.add(new TimeSeriesValue(19, 33));
        timeSeriesValues.add(new TimeSeriesValue(6, 28));
        timeSeriesValues.add(new TimeSeriesValue(7, 1));
        timeSeriesValues.add(new TimeSeriesValue(9, 18));
        timeSeriesValues.add(new TimeSeriesValue(11, 11));
        timeSeriesValues.add(new TimeSeriesValue(12, 13));
        timeSeriesValues.add(new TimeSeriesValue(14, 25));
        timeSeriesValues.add(new TimeSeriesValue(15, 15));
        timeSeriesValues.add(new TimeSeriesValue(16, 22));
        timeSeriesValues.add(new TimeSeriesValue(10, 32));
        timeSeriesValues.add(new TimeSeriesValue(18, 22));
        timeSeriesValues.add(new TimeSeriesValue(17, 3));
        timeSeriesValues.add(new TimeSeriesValue(21, 6));
        timeSeriesValues.add(new TimeSeriesValue(20, 12));
        timeSeriesValues.add(new TimeSeriesValue(23, 4));

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application, timeSeriesValues);

        LogResponse gfrExceptionLog = new LogResponse(application, logType, exceptionsPerTime);

        latestLog.addLog(gfrExceptionLog);
    }
}

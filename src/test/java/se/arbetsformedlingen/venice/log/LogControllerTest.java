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

import java.time.LocalDateTime;
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
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T22:17"), 3));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T01:17"), 7));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T13:17"), 41));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T02:17"), 3));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T08:17"), 17));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T04:17"), 43));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T03:17"), 17));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T05:17"), 42));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T00:17"), 4));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T19:17"), 33));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T06:17"), 28));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T07:17"), 1));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T09:17"), 18));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T11:17"), 11));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T12:17"), 13));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T14:17"), 25));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T15:17"), 15));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T16:17"), 22));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T10:17"), 32));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T18:17"), 22));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T17:17"), 3));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T21:17"), 6));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T20:17"), 12));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T23:17"), 4));

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application, timeSeriesValues);

        LogResponse gfrExceptionLog = new LogResponse(application, logType, exceptionsPerTime);

        latestLog.addLog(gfrExceptionLog);
    }
}

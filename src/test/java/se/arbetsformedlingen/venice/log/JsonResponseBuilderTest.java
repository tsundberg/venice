package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonResponseBuilderTest {

    @Test
    public void exceptions_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        Application application = new Application("gfr");
        LogType logType = new LogType("exception");

        List<TimeSeriesValue> timeSeriesValues = new LinkedList<>();
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T22:17:42"), 3));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T01:17:42"), 7));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T13:17:42"), 41));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T02:17:42"), 3));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T08:17:42"), 17));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T04:17:42"), 43));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T03:17:42"), 17));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T05:17:42"), 42));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T00:17:42"), 4));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T19:17:42"), 33));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T06:17:42"), 28));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T07:17:42"), 1));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T09:17:42"), 18));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T11:17:42"), 11));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T12:17:42"), 13));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T14:17:42"), 25));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T15:17:42"), 15));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T16:17:42"), 22));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T10:17:42"), 32));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T18:17:42"), 22));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T17:17:42"), 3));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T21:17:42"), 6));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T20:17:42"), 12));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T23:17:42"), 4));

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application, timeSeriesValues);

        LogResponse logResponse = new LogResponse(application, logType, exceptionsPerTime);

        String actual = builder.build(logResponse);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("exception");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("timeSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(24);

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(7);
        assertThat(actualTimeValue.get("time")).isEqualTo("2017-09-21T07:00");
        assertThat(actualTimeValue.get("value")).isEqualTo(1);
    }

    @Test
    public void load_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        Application application = new Application("gfr");
        LogType logType = new LogType("application-load");

        ApplicationLoad series = new ApplicationLoad(
                new HostValue(new Host("L7700746"), 2345672L),
                new HostValue(new Host("L7700747"), 3235159L),
                new HostValue(new Host("L7700770"), 1335652L)
        );

        LogResponse logResponse = new LogResponse(application, logType, series);

        String actual = builder.build(logResponse);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("application-load");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("loadSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(3);

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(2);
        assertThat(actualTimeValue.get("host")).isEqualTo("L7700770");
        assertThat(actualTimeValue.get("load")).isEqualTo(1335652);
    }

    @Test
    public void calls_per_ws_version_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        Application application = new Application("gfr");
        LogType logType = new LogType("webservice-load");

        WebserviceLoad series = new WebserviceLoad(
                new WebserviceValue(new Webservice("WS2"), 2302L),
                new WebserviceValue(new Webservice("WS3"), 54333L),
                new WebserviceValue(new Webservice("WS4"), 231432L),
                new WebserviceValue(new Webservice("WS5"), 57439L),
                new WebserviceValue(new Webservice("WS6"), 5403L)
        );

        LogResponse logResponse = new LogResponse(application, logType, series);

        String actual = builder.build(logResponse);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("webservice-load");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("loadSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(5);

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(2);
        assertThat(actualTimeValue.get("version")).isEqualTo("WS4");
        assertThat(actualTimeValue.get("calls")).isEqualTo(231432);
    }

    @Test
    public void calls_per_consuming_system_per_hour_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        Application application = new Application("gfr");
        LogType logType = new LogType("consuming-system");


        List<ConsumingSystemValue> values = getConsumingSystemValues();

        ConsumingSystemLoad series = new ConsumingSystemLoad(application, values);

        LogResponse logResponse = new LogResponse(application, logType, series);

        String actual = builder.build(logResponse);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("consuming-system");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("loadSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(48);

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(2);
        assertThat(actualTimeValue.get("system")).isEqualTo("KA");
        assertThat(actualTimeValue.get("calls")).isEqualTo(27);
        assertThat(actualTimeValue.get("time")).isEqualTo("2017-09-21T01:00");
    }

    private List<ConsumingSystemValue> getConsumingSystemValues() {
        List<ConsumingSystemValue> values = new LinkedList<>();
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T00:17:00"), 32L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T01:17:00"), 27L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T02:17:00"), 33L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T03:17:00"), 17L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T04:17:00"), 44L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T05:17:00"), 24L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T06:17:00"), 25L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T07:17:00"), 300L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T08:17:00"), 360L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T09:17:00"), 3434L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T10:17:00"), 3223L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T11:17:00"), 3432L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T12:17:00"), 3532L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T13:17:00"), 3242L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T14:17:00"), 3276L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T15:17:00"), 3289L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T16:17:00"), 2242L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T17:17:00"), 1548L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T18:17:00"), 420L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T19:17:00"), 323L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T20:17:00"), 32L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T21:17:00"), 33L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T22:17:00"), 12L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("KA"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T23:17:00"), 3L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T00:42:17"), 232L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T01:42:17"), 227L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T02:42:17"), 233L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T03:42:17"), 217L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T04:42:17"), 244L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T05:42:17"), 224L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T06:42:17"), 225L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T07:42:17"), 2300L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T08:42:17"), 2360L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T09:42:17"), 23434L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T10:42:17"), 23223L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T11:42:17"), 23432L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T12:42:17"), 23532L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T13:42:17"), 23242L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T14:42:17"), 23276L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T15:42:17"), 23289L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T16:42:17"), 22242L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T17:42:17"), 21548L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T18:42:17"), 2420L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T19:42:17"), 2323L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T20:42:17"), 232L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T21:42:17"), 233L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T22:42:17"), 212L)));
        values.add(new ConsumingSystemValue(new ConsumingSystem("gfr"), new TimeSeriesValue(LocalDateTime.parse("2017-09-21T23:42:17"), 23L)));
        return values;
    }
}

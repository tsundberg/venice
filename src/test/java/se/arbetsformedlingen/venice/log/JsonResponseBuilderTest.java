package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.*;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonResponseBuilderTest {

    @Test
    public void exceptions_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        Application application = new Application("gfr");
        LogType logType = new LogType("exception");

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application,
                new TimeSeriesValue(22, 3), new TimeSeriesValue(1, 7), new TimeSeriesValue(13, 41), new TimeSeriesValue(2, 3),
                new TimeSeriesValue(8, 17), new TimeSeriesValue(4, 43), new TimeSeriesValue(3, 17), new TimeSeriesValue(5, 42),
                new TimeSeriesValue(0, 4), new TimeSeriesValue(19, 33), new TimeSeriesValue(6, 28), new TimeSeriesValue(7, 1),
                new TimeSeriesValue(9, 18), new TimeSeriesValue(11, 11), new TimeSeriesValue(12, 13), new TimeSeriesValue(14, 25),
                new TimeSeriesValue(15, 15), new TimeSeriesValue(16, 22), new TimeSeriesValue(10, 32), new TimeSeriesValue(18, 22),
                new TimeSeriesValue(17, 3), new TimeSeriesValue(21, 6), new TimeSeriesValue(20, 12), new TimeSeriesValue(23, 4)
        );

        LogResponse logResponse = new LogResponse(application, logType, exceptionsPerTime);

        String actual = builder.build(logResponse);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("exception");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("timeSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(24);

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(7);
        assertThat(actualTimeValue.get("time")).isEqualTo(7);
        assertThat(actualTimeValue.get("value")).isEqualTo(1);
    }

    @Test
    public void load_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        Application application = new Application("gfr");
        LogType logType = new LogType("application-load");

        ApplicationLoad series = new ApplicationLoad(application,
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

        WebserviceLoad series = new WebserviceLoad(application,
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

        ConsumingSystemLoad series = new ConsumingSystemLoad(application,
                new ConsumingSystemValue(new ConsumingSystem("KA"), 0, 32L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 1, 27L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 2, 33L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 3, 17L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 4, 44L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 5, 24L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 6, 25L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 7, 300L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 8, 360L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 9, 3434L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 10, 3223L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 11, 3432L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 12, 3532L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 13, 3242L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 14, 3276L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 15, 3289L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 16, 2242L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 17, 1548L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 18, 420L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 19, 323L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 20, 32L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 21, 33L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 22, 12L),
                new ConsumingSystemValue(new ConsumingSystem("KA"), 23, 3L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 0, 232L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 1, 227L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 2, 233L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 3, 217L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 4, 244L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 5, 224L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 6, 225L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 7, 2300L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 8, 2360L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 9, 23434L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 10, 23223L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 11, 23432L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 12, 23532L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 13, 23242L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 14, 23276L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 15, 23289L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 16, 22242L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 17, 21548L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 18, 2420L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 19, 2323L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 20, 232L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 21, 233L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 22, 212L),
                new ConsumingSystemValue(new ConsumingSystem("gfr"), 23, 23L)
        );

        LogResponse logResponse = new LogResponse(application, logType, series);

        String actual = builder.build(logResponse);

        System.out.println(actual);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("consuming-system");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("loadSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(48);

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(2);
        assertThat(actualTimeValue.get("system")).isEqualTo("KA");
        assertThat(actualTimeValue.get("calls")).isEqualTo(27);
        assertThat(actualTimeValue.get("time")).isEqualTo(1);
    }
}

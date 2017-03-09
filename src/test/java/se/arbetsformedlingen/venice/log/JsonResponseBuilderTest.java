package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.common.Host;
import se.arbetsformedlingen.venice.common.Webservice;
import se.arbetsformedlingen.venice.model.*;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonResponseBuilderTest {

    @Test
    public void exceptions_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
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

        LogResponse logResponse = new LogResponse(application, logType, timeSeries);

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
                new HostLoadValue(new Host("L7700746"), 2345672L),
                new HostLoadValue(new Host("L7700747"), 3235159L),
                new HostLoadValue(new Host("L7700770"), 1335652L)
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
                new WebserviceLoadValue(new Webservice("WS2"), 2302L),
                new WebserviceLoadValue(new Webservice("WS3"), 54333L),
                new WebserviceLoadValue(new Webservice("WS4"), 231432L),
                new WebserviceLoadValue(new Webservice("WS5"), 57439L),
                new WebserviceLoadValue(new Webservice("WS6"), 5403L)
        );

        LogResponse logResponse = new LogResponse(application, logType, series);

        String actual = builder.build(logResponse);

        System.out.println(actual);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("webservice-load");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("loadSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(5);

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(2);
        assertThat(actualTimeValue.get("version")).isEqualTo("WS4");
        assertThat(actualTimeValue.get("calls")).isEqualTo(231432);
    }

}













































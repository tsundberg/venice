package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeries;
import se.arbetsformedlingen.venice.model.TimeValue;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonResponseBuilderTest {

    @Test
    public void exceptions_for_gfr() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        Application application = new Application("gfr");
        LogType logType = new LogType("exception");

        TimeSeries timeSeries = new TimeSeries(
                new TimeValue(22, 3), new TimeValue(1, 7), new TimeValue(13, 41), new TimeValue(2, 3),
                new TimeValue(8, 17), new TimeValue(4, 43), new TimeValue(3, 17), new TimeValue(5, 42),
                new TimeValue(0, 4), new TimeValue(19, 33), new TimeValue(6, 28), new TimeValue(7, 1),
                new TimeValue(9, 18), new TimeValue(11, 11), new TimeValue(12, 13), new TimeValue(14, 25),
                new TimeValue(15, 15), new TimeValue(16, 22), new TimeValue(10, 32), new TimeValue(18, 22),
                new TimeValue(17, 3), new TimeValue(21, 6), new TimeValue(20, 12), new TimeValue(23, 4)
        );

        LogResponse logResponse = new LogResponse(application, logType, timeSeries);

        String actual = builder.build(logResponse);

        System.out.println(actual);

        JSONObject actualJson = new JSONObject(actual);

        assertThat(actualJson.get("application")).isEqualTo("gfr");
        assertThat(actualJson.get("logType")).isEqualTo("exception");

        JSONArray actualTimeSeries = (JSONArray) actualJson.get("timeSeries");
        assertThat(actualTimeSeries.length()).isEqualTo(24) ;

        JSONObject actualTimeValue = (JSONObject) actualTimeSeries.get(7);
        assertThat(actualTimeValue.get("time")).isEqualTo(7);
        assertThat(actualTimeValue.get("value")).isEqualTo(1);
    }

}

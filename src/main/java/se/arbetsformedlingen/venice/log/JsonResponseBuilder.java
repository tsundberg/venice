package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.model.TimeValue;

public class JsonResponseBuilder {
    public String build(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        response.put("application", logResponse.getApplication());
        response.put("logType", logResponse.getLogType());

        JSONArray timeSeries = new JSONArray();

        for (TimeValue value : logResponse.getTimeValues()) {
            JSONObject timeValue = new JSONObject();
            timeValue.put("time", value.getTime());
            timeValue.put("value", value.getValue());
            timeSeries.put(timeValue);
        }

        response.put("timeSeries", timeSeries);

        return response.toString();
    }
}

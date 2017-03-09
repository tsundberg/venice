package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.model.HostLoad;
import se.arbetsformedlingen.venice.model.TimeValue;

public class JsonResponseBuilder {

    // todo Skapa speciella LogResponse object som klarar respektive typ. Skapa sen en builder för varje specific typ.

    public String build(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        if (logResponse.getLogTypeName().equals("exception")) {
            response = getExceptionJson(logResponse);
        }

        if (logResponse.getLogTypeName().equals("load")) {
            response = getLoadJson(logResponse);
        }

        return response.toString();
    }

    private JSONObject getExceptionJson(LogResponse logResponse) {
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
        return response;
    }

    private JSONObject getLoadJson(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        response.put("application", logResponse.getApplication());
        response.put("logType", logResponse.getLogType());

        JSONArray loadSeries = new JSONArray();

        for (HostLoad value : logResponse.getApplicationLoadValues()) {
            JSONObject timeValue = new JSONObject();
            timeValue.put("host", value.getHost());
            timeValue.put("load", value.getLoad());
            loadSeries.put(timeValue);
        }

        response.put("loadSeries", loadSeries);
        return response;
    }
}

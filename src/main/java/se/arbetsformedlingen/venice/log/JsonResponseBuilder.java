package se.arbetsformedlingen.venice.log;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.model.ConsumingSystemValue;
import se.arbetsformedlingen.venice.model.HostValue;
import se.arbetsformedlingen.venice.model.TimeSeriesValue;
import se.arbetsformedlingen.venice.model.WebserviceValue;

class JsonResponseBuilder {

    // todo Skapa speciella LogResponse object som klarar respektive typ. Skapa sen en builder för varje specific typ.

    String build(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        if (logResponse.getLogTypeName().equals("exception")) {
            response = getExceptionJson(logResponse);
        }

        if (logResponse.getLogTypeName().equals("application-load")) {
            response = getApplicationLoadJson(logResponse);
        }

        if (logResponse.getLogTypeName().equals("webservice-load")) {
            response = getWebservieLoadJson(logResponse);
        }

        if (logResponse.getLogTypeName().equals("consuming-system")) {
            response = getConsumingSystemLoadJson(logResponse);
        }

        return response.toString();
    }

    private JSONObject getExceptionJson(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        response.put("application", logResponse.getApplication());
        response.put("logType", logResponse.getLogType());

        JSONArray timeSeries = new JSONArray();

        for (TimeSeriesValue value : logResponse.getExceptionPerHour()) {
            JSONObject timeValue = new JSONObject();
            timeValue.put("time", value.getTime());
            timeValue.put("value", value.getValue());
            timeSeries.put(timeValue);
        }

        response.put("timeSeries", timeSeries);
        return response;
    }

    private JSONObject getApplicationLoadJson(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        response.put("application", logResponse.getApplication());
        response.put("logType", logResponse.getLogType());

        JSONArray loadSeries = new JSONArray();

        for (HostValue value : logResponse.getApplicationLoadValues()) {
            JSONObject timeValue = new JSONObject();
            timeValue.put("host", value.getHost());
            timeValue.put("load", value.getLoad());
            loadSeries.put(timeValue);
        }

        response.put("loadSeries", loadSeries);
        return response;
    }

    private JSONObject getWebservieLoadJson(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        response.put("application", logResponse.getApplication());
        response.put("logType", logResponse.getLogType());

        JSONArray loadSeries = new JSONArray();

        for (WebserviceValue value : logResponse.getWebserviceLoadValues()) {
            JSONObject timeValue = new JSONObject();
            timeValue.put("version", value.getWebservice());
            timeValue.put("calls", value.getCalls());
            loadSeries.put(timeValue);
        }

        response.put("loadSeries", loadSeries);
        return response;
    }

    private JSONObject getConsumingSystemLoadJson(LogResponse logResponse) {
        JSONObject response = new JSONObject();

        response.put("application", logResponse.getApplication());
        response.put("logType", logResponse.getLogType());

        JSONArray loadSeries = new JSONArray();

        for (ConsumingSystemValue value : logResponse.getConsumingSystemValues()) {
            JSONObject timeValue = new JSONObject();
            timeValue.put("system", value.getConsumingSystem());
            timeValue.put("calls", value.getCalls());
            timeValue.put("time", value.getTime());
            loadSeries.put(timeValue);
        }

        response.put("loadSeries", loadSeries);
        return response;
    }
}

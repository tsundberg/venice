package se.arbetsformedlingen.venice.log.elasticsearch;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.ExceptionsPerTime;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeriesValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static se.arbetsformedlingen.venice.log.elasticsearch.DateUtil.yesterday;

/**
 * Find Exceptions for each system in production
 */
public class FindExceptions implements Supplier<LogResponse> {
    private ElasticSearchClient client;
    private Application application;
    private Configuration configuration;

    public FindExceptions(ElasticSearchClient client, Application application, Configuration configuration) {
        this.client = client;
        this.application = application;
        this.configuration = configuration;
    }

    @Override
    public LogResponse get() {
        Map<String, Integer> exceptionsPerHour = new HashMap<>();
        initiate(exceptionsPerHour);

        List<String> indexes = client.getLogstashIndexes();

        for (int index = 0; index < 2; index++) {
            String searchIndex = indexes.get(index);
            String action = getAction(searchIndex);

            String json = client.getJson(action);
            JSONObject jsonObject = new JSONObject(json);

            collectExceptions(jsonObject, exceptionsPerHour);
        }

        LogType logType = new LogType("exception");
        List<TimeSeriesValue> timeSeriesValues = new LinkedList<>();

        for (String key : exceptionsPerHour.keySet()) {
            Integer value = exceptionsPerHour.get(key);
            timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse(key), value));
        }

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application, timeSeriesValues);

        return new LogResponse(application, logType, exceptionsPerTime);
    }

    private String getAction(String index) {
        String applicationFilter = configuration.getApplicationLoadSearchString(application.getName());
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": {\n" +
                "        \"wildcard\": {\n" +
                "          \"jboss_app_ex\": \"*" + applicationFilter + "\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"filter\": {\n" +
                "        \"term\": {\n" +
                "          \"level\": \"ERROR\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String queryArgument = "?source=" + urlEncode(query);

        return "/" + index + "/_search" + queryArgument;
    }

    private void collectExceptions(JSONObject json, Map<String, Integer> exceptionsPerHour) {
        JSONObject hits = json.getJSONObject("hits");
        JSONArray hitList = hits.getJSONArray("hits");

        for (int index = 0; index < hitList.length(); index++) {
            JSONObject exception = hitList.getJSONObject(index);

            JSONObject logEntry = exception.getJSONObject("_source");

            String timeStamp = logEntry.getString("@timestamp");

            addEvent(exceptionsPerHour, timeStamp);
        }
    }

    private String urlEncode(String query) {
        // todo extract to common utility
        try {
            return URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initiate(Map<String, Integer> exceptionPerHour) {
        for (int hour = 0; hour < 24; hour++) {
            String key = TimeSeriesValue.normalized(LocalDateTime.now().minusHours(hour)).toString();
            exceptionPerHour.put(key, 0);
        }
    }

    void addEvent(Map<String, Integer> exceptionPerHour, String timeStamp) {
        LocalDateTime eventTime = DateUtil.getCetDateTimeFromUtc(timeStamp);

        if (eventTime.isAfter(yesterday())) {
            String key = TimeSeriesValue.normalized(eventTime).toString();
            Integer hits = exceptionPerHour.get(key);

            if (hits == null) {
                hits = 1;
            } else {
                hits = hits + 1;
            }

            exceptionPerHour.put(key, hits);
        }
    }
}
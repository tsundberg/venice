package se.arbetsformedlingen.venice.log.elasticsearch;

import org.json.JSONArray;
import org.json.JSONObject;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Find the load for each host in production
 */
public class FindHostLoad implements Supplier<LogResponse> {
    private ElasticSearchClient client;
    private Application application;
    private Configuration configuration;

    FindHostLoad() {
    }

    public FindHostLoad(ElasticSearchClient client, Application application, Configuration configuration) {
        this.client = client;
        this.application = application;
        this.configuration = configuration;
    }

    @Override
    public LogResponse get() {
        List<String> indexes = client.getLogstashIndexes();

        Map<Host, Long> loadValues = new HashMap<>();

        for (int index = 0; index < 2; index++) {
            String searchIndex = indexes.get(index);
            String action = getAction(searchIndex);
            String json = client.getJson(action);

            HostValue[] values = getHostValues(json);

            aggregateLoad(loadValues, values);
        }

        HostValue[] values = getAggregatedLoad(loadValues);

        LogType logType = new LogType("application-load");
        ApplicationLoad applicationLoad = new ApplicationLoad(values);

        return new LogResponse(application, logType, applicationLoad);
    }

    private void aggregateLoad(Map<Host, Long> loadValues, HostValue[] values) {
        for (HostValue val : values) {
            Host host = val.getHost();
            Long load = val.getLoad();

            Long current = loadValues.get(host);
            if (current == null) {
                current = load;
                loadValues.put(host, current);
            } else {
                current = current + load;
                loadValues.put(host, current);
            }
        }
    }

    private HostValue[] getAggregatedLoad(Map<Host, Long> loadValues) {
        HostValue[] values = new HostValue[loadValues.size()];
        int index = 0;
        for (Host key : loadValues.keySet()) {
            Long load = loadValues.get(key);
            values[index++] = new HostValue(key, load);
        }
        return values;
    }

    private String getAction(String index) {
        String applicationFilter = configuration.getApplicationLoadSearchString(application.getName());
        String query = "{\n" +
                "  \"size\": 0,\n" +
                "  \"query\": {\n" +
                "    \"wildcard\": {\n" +
                "      \"jboss_app_app_class\": \"" + applicationFilter + "\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"aggs\": {\n" +
                "    \"hosts\": {\n" +
                "      \"terms\": {\n" +
                "        \"field\": \"host\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String queryArgument = "?source=" + urlEncode(query);
        return "/" + index + "/_search" + queryArgument;
    }

    private String urlEncode(String query) {
        // todo extract to common utility
        try {
            return URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    HostValue[] getHostValues(String json) {
        List<HostValue> hostValues = new LinkedList<>();

        JSONObject jsonObject = new JSONObject(json);

        jsonObject = jsonObject.getJSONObject("aggregations");
        jsonObject = jsonObject.getJSONObject("hosts");
        JSONArray jsonArray = jsonObject.getJSONArray("buckets");

        for (int index = 0; index < jsonArray.length(); index++) {
            jsonObject = jsonArray.getJSONObject(index);

            String host = jsonObject.getString("key");
            long count = jsonObject.getLong("doc_count");
            HostValue hostValue = new HostValue(new Host(host), count);

            hostValues.add(hostValue);
        }

        return hostValues.toArray(new HostValue[hostValues.size()]);
    }
}

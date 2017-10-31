package se.arbetsformedlingen.venice.log.elasticsearch;


import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElasticSearchClient {
    private String host;
    private String port;

    public ElasticSearchClient(String host, String port) {
        this.host = host;
        this.port = port;
    }

    String getJson(String action) {
        String uri = "http://" + host + ":" + port + action;

        Executor executor = Executor.newInstance();

        try {
            return executor
                    .execute(Request.Get(uri))
                    .returnContent()
                    .asString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    List<String> getLogstashIndexes() {
        String action = "/_stats/index,store";
        String json = getJson(action);
        JSONObject indexResponse = new JSONObject(json);
        JSONObject indices = indexResponse.getJSONObject("indices");

        List<String> indexes = new ArrayList<>();
        for (String index : indices.keySet()) {
            if (index.startsWith("logstash")) {
                indexes.add(index);
            }
        }

        indexes.sort(Collections.reverseOrder());
        return indexes;
    }

}
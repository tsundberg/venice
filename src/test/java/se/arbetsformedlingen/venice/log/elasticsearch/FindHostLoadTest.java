package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Test;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.Host;
import se.arbetsformedlingen.venice.model.HostValue;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FindHostLoadTest {
    @Test
    public void get_load_per_host() {
        ElasticSearchClient client = getClient();
        Application application = new Application("gfr");
        Configuration configuration = new Configuration("configuration.yaml");

        FindHostLoad findHostLoad = new FindHostLoad(client, application, configuration);

        LogResponse logResponse = findHostLoad.get();

        verify(client).getLogstashIndexes();

        assertThat(logResponse.getApplication()).isEqualTo(new Application("gfr"));
        assertThat(logResponse.getApplicationLoadValues()).contains(new HostValue(new Host("L7700770"), 31530L));
    }

    private ElasticSearchClient getClient() {
        List<String> indexes = new LinkedList<>();

        indexes.add("logstash-2017.10.30");
        indexes.add("logstash-2017.10.29");
        indexes.add("logstash-2017.10.28");

        ElasticSearchClient client = mock(ElasticSearchClient.class);
        when(client.getLogstashIndexes()).thenReturn(indexes);
        when(client.getJson(anyString())).thenReturn(getSampleJson());

        return client;
    }

    @Test
    public void get_host_values() {
        String json = getSampleJson();

        FindHostLoad applicationLoad = new FindHostLoad();

        HostValue[] values = applicationLoad.getHostValues(json);

        assertThat(values).contains(new HostValue(new Host("L7700747"), 9086L));
    }

    private String getSampleJson() {
        return "{\n" +
                "  \"_shards\": {\n" +
                "    \"total\": 2,\n" +
                "    \"failed\": 0,\n" +
                "    \"successful\": 2\n" +
                "  },\n" +
                "  \"hits\": {\n" +
                "    \"hits\": [],\n" +
                "    \"total\": 36657,\n" +
                "    \"max_score\": 0\n" +
                "  },\n" +
                "  \"took\": 1589,\n" +
                "  \"timed_out\": false,\n" +
                "  \"aggregations\": {\"hosts\": {\n" +
                "    \"doc_count_error_upper_bound\": 0,\n" +
                "    \"sum_other_doc_count\": 0,\n" +
                "    \"buckets\": [\n" +
                "      {\n" +
                "        \"doc_count\": 15765,\n" +
                "        \"key\": \"L7700770\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"doc_count\": 11806,\n" +
                "        \"key\": \"L7700746\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"doc_count\": 9086,\n" +
                "        \"key\": \"L7700747\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }}\n" +
                "}\n";
    }
}

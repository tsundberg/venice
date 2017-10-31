package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticSearchClientIT {

    @Test
    public void get_indexes() {
        String host = "elk.arbetsformedlingen.se";
        String port = "9200";

        ElasticSearchClient searchClient = new ElasticSearchClient(host, port);

        List<String> indexes = searchClient.getLogstashIndexes();

        assertThat(indexes).isNotEmpty();

        assertThat(indexes.get(0)).startsWith("logstash");

        for (String index : indexes) {
            System.out.println(index);
        }
    }


}

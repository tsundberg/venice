package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindConsumingSystemLoadTest {

    private Client client;

    @Before
    public void get_client() {
        Settings settings = ElasticSearchClient.getSettings();
        client = ElasticSearchClient.getClient(settings);
    }

    @Test
    @Ignore
    public void find_application_load() {
        FindConsumingSystemLoad findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("cpr"));
        LogResponse logResponse = findWebserviceLoad.get();

        findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("gfr"));
        logResponse = findWebserviceLoad.get();

        findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("geo"));
        logResponse = findWebserviceLoad.get();

        findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("agselect"));
        logResponse = findWebserviceLoad.get();

        System.out.println();
    }

    @Test
    public void extract_hour() {
        FindConsumingSystemLoad findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("cpr"));

        Integer actual = findWebserviceLoad.getHour("2017-03-13T22:00:00.000Z");

        assertThat(actual).isEqualTo(23);
    }

    @Test
    public void extract_hour_past_midnight() {
        FindConsumingSystemLoad findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("cpr"));

        Integer actual = findWebserviceLoad.getHour("2017-03-13T23:00:00.000Z");

        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void get_last_days_values() {
        FindConsumingSystemLoad findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("cpr"));

        List<Histogram.Bucket> sample = new LinkedList<>();
        for (int i = 0; i < 48; i++) {
            sample.add(getBucket());
        }

        List<? extends Histogram.Bucket> actual = findWebserviceLoad.getLastDaysValues(sample);

        assertThat(actual.size()).isEqualTo(24);
    }

    @Test
    public void get_last_days_values_short() {
        FindConsumingSystemLoad findWebserviceLoad = new FindConsumingSystemLoad(client, new Application("cpr"));

        List<Histogram.Bucket> sample = new LinkedList<>();
        for (int i = 0; i < 12; i++) {
            sample.add(getBucket());
        }

        List<? extends Histogram.Bucket> actual = findWebserviceLoad.getLastDaysValues(sample);

        assertThat(actual.size()).isEqualTo(12);
    }

    private Histogram.Bucket getBucket() {
        return new Histogram.Bucket() {
            @Override
            public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
                return null;
            }

            @Override
            public void readFrom(StreamInput in) throws IOException {
            }

            @Override
            public void writeTo(StreamOutput out) throws IOException {
            }

            @Override
            public Object getKey() {
                return null;
            }

            @Override
            public String getKeyAsString() {
                return null;
            }

            @Override
            public long getDocCount() {
                return -1;
            }

            @Override
            public Aggregations getAggregations() {
                return null;
            }

            @Override
            public Object getProperty(String containingAggName, List<String> path) {
                return null;
            }
        };
    }

}

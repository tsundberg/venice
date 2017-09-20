package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;

public class FindExceptionsTest {
    private Client client;

    @Before
    public void get_client() {
        Settings settings = ElasticSearchClient.getSettings();
        client = ElasticSearchClient.getClient(settings);
    }

    @Test
    public void get_yesterday_hour() {
        FindExceptions findExceptions = new FindExceptions(client, new Application("geo"));

        LocalDateTime actual = findExceptions.yesterday();

        assertThat(actual.getSecond()).isEqualTo(0);
    }

    @Test
    @Ignore
    public void find_exceptions() {
        FindExceptions findExceptions = new FindExceptions(client, new Application("geo"));
        findExceptions.get();
    }
}

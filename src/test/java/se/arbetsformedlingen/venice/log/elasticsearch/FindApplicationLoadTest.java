package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;

public class FindApplicationLoadTest {
    private Client client;

    @Before
    public void get_client() {
        Settings settings = ElasticSearchClient.getSettings();
        client = ElasticSearchClient.getClient(settings);
    }

    @Test
    @Ignore
    public void find_application_load() {
        FindApplicationLoad findExceptions = new FindApplicationLoad(client, new Application("gfr"));
        LogResponse logResponse = findExceptions.get();

        System.out.println(logResponse);
    }
}

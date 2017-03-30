package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;

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
        Configuration configuration = new Configuration();
        FindApplicationLoad findExceptions = new FindApplicationLoad(client, new Application("gfr"), configuration);
        LogResponse logResponse = findExceptions.get();

        System.out.println(logResponse);
    }
}

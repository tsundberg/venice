package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;

public class FindWebserviceLoadTest {
    private Client client;

    @Before
    public void get_client() {
        Settings settings = ElasticSearchClient.getSettings();
        client = ElasticSearchClient.getClient(settings);
    }

    @Test
    @Ignore
    public void find_application_load() {
        FindWebserviceLoad findWebserviceLoad = new FindWebserviceLoad(client, new Application("cpr"));
        LogResponse logResponse = findWebserviceLoad.get();

        findWebserviceLoad = new FindWebserviceLoad(client, new Application("gfr"));
        logResponse = findWebserviceLoad.get();

        findWebserviceLoad = new FindWebserviceLoad(client, new Application("geo"));
        logResponse = findWebserviceLoad.get();
    }
}

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
        Settings settings = FatElasticSearchClient.getSettings();
        client = FatElasticSearchClient.getClient(settings);
    }

    @Test
    @Ignore
    public void find_application_load() {
        Configuration configuration = new Configuration("no file");
        Application application = new Application("gfr");
        FindApplicationLoad findApplicationLoad = new FindApplicationLoad(client, application, configuration);
        LogResponse logResponse = findApplicationLoad.get();

        System.out.println(logResponse);
    }
}

package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.LogType;

import static org.assertj.core.api.Assertions.assertThat;

public class FindExceptionsIT {

    @Test
    @Ignore
    public void find_exceptions_for_gfr() {
        String host = "elk.arbetsformedlingen.se";
        String port = "9200";

        ElasticSearchClient client = new ElasticSearchClient(host, port);
        Application application = new Application("gfr");
        Configuration configuration = new Configuration("configuration.yaml");

        FindExceptions findExceptions = new FindExceptions(client, application, configuration);

        LogResponse logResponse = findExceptions.get();

        assertThat(logResponse.getLogType()).isEqualTo(new LogType("exception"));
    }
}


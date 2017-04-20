package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTermsBuilder;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class FindApplicationLoad implements Supplier<LogResponse> {
    private Application application;
    private Configuration configuration;

    private Client client;

    public FindApplicationLoad(Client client, Application application, Configuration configuration) {
        this.client = client;
        this.application = application;
        this.configuration = configuration;
    }

    @Override
    public LogResponse get() {
        String queryString = configuration.getApplicationLoadSearchString(application);
        QueryBuilder jboss_app_app_class = queryStringQuery(queryString)
                .analyzeWildcard(true);

        SignificantTermsBuilder significatTerms = AggregationBuilders
                .significantTerms("calls per host")
                .field("host");

        SearchResponse response = client.prepareSearch(ElasticSearchClient.today(), ElasticSearchClient.yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(significatTerms)
                .execute()
                .actionGet();

        SignificantTerms callsPerHost = response.getAggregations().get("calls per host");
        HostValue[] values = getHostValues(callsPerHost);

        LogType logType = new LogType("application-load");
        ApplicationLoad applicationLoad = new ApplicationLoad(values);

        return new LogResponse(application, logType, applicationLoad);
    }

    private HostValue[] getHostValues(SignificantTerms callsPerHost) {
        List<HostValue> hostValues = new LinkedList<>();
        for (SignificantTerms.Bucket bucket : callsPerHost.getBuckets()) {
            String hostName = bucket.getKeyAsString();
            Long load = bucket.getSubsetDf();
            HostValue hostValue = new HostValue(new Host(hostName), load);
            hostValues.add(hostValue);
        }

        return hostValues.toArray(new HostValue[hostValues.size()]);
    }
}

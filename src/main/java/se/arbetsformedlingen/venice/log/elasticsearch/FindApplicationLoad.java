package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTermsBuilder;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class FindApplicationLoad extends ElasticSearchClient implements Supplier<LogResponse> {
    private Application application;

    private Map<Application, String> queryStrings = new HashMap<>();

   public FindApplicationLoad(Application application) {
        this.application = application;

        queryStrings.put(new Application("gfr"), "se.arbetsformedlingen.foretag*");
        queryStrings.put(new Application("geo"), "se.arbetsformedlingen.geo*");
        queryStrings.put(new Application("cpr"), "se.arbetsformedlingen.cpr*");
        queryStrings.put(new Application("agselect"), "se.arbetsformedlingen.gfr.ma*");
    }

    @Override
    public LogResponse get() {
        Settings settings = getSettings();

        Client client = getClient(settings);

        String queryString = queryStrings.get(application);
        QueryBuilder jboss_app_app_class = queryStringQuery(queryString)
                .analyzeWildcard(true);

        SignificantTermsBuilder significatTerms = AggregationBuilders
                .significantTerms("calls per host")
                .field("host");

        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(significatTerms)
                .execute()
                .actionGet();

        SignificantTerms callsPerHost = response.getAggregations().get("calls per host");
        HostValue[] values = getHostValues(callsPerHost);

        LogType logType = new LogType("application-load");
        ApplicationLoad applicationLoad = new ApplicationLoad(application, values);

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

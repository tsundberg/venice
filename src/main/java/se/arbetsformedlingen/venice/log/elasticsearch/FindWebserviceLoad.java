package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
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

public class FindWebserviceLoad implements Supplier<LogResponse> {
    private Application application;

    private Map<Application, String> queryStrings = new HashMap<>();
    private Map<Application, String> significantField = new HashMap<>();

    private Client client;

    public FindWebserviceLoad(Client client, Application application) {
        this.application = application;
        this.client = client;

        // todo config
        queryStrings.put(new Application("gfr"), "se.arbetsformedlingen.foretag.gfrws*");
        queryStrings.put(new Application("geo"), "se.arbetsformedlingen.geo*");
        queryStrings.put(new Application("cpr"), "se.arbetsformedlingen.cpr*");

        significantField.put(new Application("gfr"), "jboss_app_app_op");
        significantField.put(new Application("geo"), "jboss_app_app_name");
        significantField.put(new Application("cpr"), "jboss_app_app_op");
    }

    @Override
    public LogResponse get() {
        String queryString = queryStrings.get(application);
        QueryBuilder jboss_app_app_class = queryStringQuery(queryString)
                .analyzeWildcard(true);

        String significantField = this.significantField.get(application);
        SignificantTermsBuilder significatTerms = AggregationBuilders
                .significantTerms("calls per webservice")
                .field(significantField);

        SearchResponse response = client.prepareSearch(FatElasticSearchClient.today(), FatElasticSearchClient.yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(significatTerms)
                .execute()
                .actionGet();

        SignificantTerms callsPerWebService = response.getAggregations().get("calls per webservice");

        WebserviceValue[] values = getHostValues(callsPerWebService);
        LogType logType = new LogType("webservice-load");
        WebserviceLoad webserviceLoad = new WebserviceLoad(values);

        return new LogResponse(application, logType, webserviceLoad);
    }

    private WebserviceValue[] getHostValues(SignificantTerms callsPerHost) {
        List<WebserviceValue> hostValues = new LinkedList<>();
        for (SignificantTerms.Bucket bucket : callsPerHost.getBuckets()) {
            String webServiceName = bucket.getKeyAsString();
            Long load = bucket.getSubsetDf();
            WebserviceValue hostValue = new WebserviceValue(new Webservice(webServiceName), load);
            hostValues.add(hostValue);
        }

        return hostValues.toArray(new WebserviceValue[hostValues.size()]);
    }
}

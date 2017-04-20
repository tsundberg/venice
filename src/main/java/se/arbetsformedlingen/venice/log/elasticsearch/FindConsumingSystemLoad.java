package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
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

public class FindConsumingSystemLoad implements Supplier<LogResponse> {
    private Application application;

    private Map<Application, String> queryStrings = new HashMap<>();
    private Map<Application, String> significantField = new HashMap<>();

    private Client client;

    public FindConsumingSystemLoad(Client client, Application application) {
        this.client = client;
        this.application = application;

        queryStrings.put(new Application("gfr"), "*.service.GFRServiceEndpointBase");
        queryStrings.put(new Application("geo"), "se.arbetsformedlingen.geo*");
        queryStrings.put(new Application("cpr"), "se.arbetsformedlingen.cpr*");
        queryStrings.put(new Application("agselect"), "se.arbetsformedlingen.gfr.ma*");

        significantField.put(new Application("gfr"), "jboss_app_message");
    }

    @Override
    public LogResponse get() {
        String queryString = queryStrings.get(application);
        QueryBuilder jboss_app_app_class = queryStringQuery(queryString)
                .analyzeWildcard(true);

        if (application.equals(new Application("gfr"))) {
            return loadPerConsumingSystem(client, jboss_app_app_class);
        } else {
            return loadPerHour(client, jboss_app_app_class);
        }
    }

    private LogResponse loadPerConsumingSystem(Client client, QueryBuilder jboss_app_app_class) {
        String significantField = this.significantField.get(application);
        SignificantTermsBuilder significatTerms = AggregationBuilders
                .significantTerms("calls per webservice")
                .field(significantField);

        DateHistogramBuilder histogram = AggregationBuilders
                .dateHistogram("calls per hour")
                .subAggregation(significatTerms)
                .field("@timestamp")
                .interval(DateHistogramInterval.HOUR);

        SearchResponse response = client.prepareSearch(ElasticSearchClient.today(), ElasticSearchClient.yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(histogram)
                .execute()
                .actionGet();

        Histogram webbApp = response.getAggregations().get("calls per hour");

        List<ConsumingSystemValue> consumingSystemValues = new LinkedList<>();
        List<? extends Histogram.Bucket> lastDaysValue = getLastDaysValues(webbApp.getBuckets());
        for (Histogram.Bucket bucket : lastDaysValue) {
            Aggregations aggregations = bucket.getAggregations();
            SignificantTerms callsPerWebService = aggregations.get("calls per webservice");

            Integer hour = getHour(bucket.getKeyAsString());
            List<ConsumingSystemValue> values = getConsumingsystemsValues(callsPerWebService, hour);

            consumingSystemValues.addAll(values);
        }

        LogType logType = new LogType("consuming-system");
        ConsumingSystemLoad consumingSystemLoad = new ConsumingSystemLoad(application, consumingSystemValues);

        return new LogResponse(application, logType, consumingSystemLoad);
    }

    private LogResponse loadPerHour(Client client, QueryBuilder jboss_app_app_class) {
        // those systems where we don't log the calling system will get the load per hour instead
        DateHistogramBuilder histogram = AggregationBuilders
                .dateHistogram("calls per hour")
                .field("@timestamp")
                .interval(DateHistogramInterval.HOUR);

        SearchResponse response = client.prepareSearch(ElasticSearchClient.today(), ElasticSearchClient.yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(histogram)
                .execute()
                .actionGet();

        Histogram webbApp = response.getAggregations().get("calls per hour");

        List<ConsumingSystemValue> consumingSystemValues = new LinkedList<>();

        List<? extends Histogram.Bucket> lastDaysValue = getLastDaysValues(webbApp.getBuckets());
        for (Histogram.Bucket bucket : lastDaysValue) {
            Integer hour = getHour(bucket.getKeyAsString());
            Long load = bucket.getDocCount();
            ConsumingSystemValue consumingSystemValue = new ConsumingSystemValue(new ConsumingSystem(""), hour, load);

            consumingSystemValues.add(consumingSystemValue);
        }

        LogType logType = new LogType("consuming-system");
        ConsumingSystemLoad consumingSystemLoad = new ConsumingSystemLoad(application, consumingSystemValues);

        return new LogResponse(application, logType, consumingSystemLoad);
    }

    private List<ConsumingSystemValue> getConsumingsystemsValues(SignificantTerms callsPerHost, Integer time) {
        List<ConsumingSystemValue> consumingSystems = new LinkedList<>();
        for (SignificantTerms.Bucket bucket : callsPerHost.getBuckets()) {
            String consumingSystemName = bucket.getKeyAsString();
            Long load = bucket.getSubsetDf();
            ConsumingSystem consumingSystem = new ConsumingSystem(consumingSystemName);

            consumingSystems.add(new ConsumingSystemValue(consumingSystem, time, load));
        }

        return consumingSystems;
    }

    List<? extends Histogram.Bucket> getLastDaysValues(List<? extends Histogram.Bucket> buckets) {
        int size = buckets.size();
        int start = size - 24;

        while(start < 0) {
            start++;
        }

        return buckets.subList(start, size);
    }

    Integer getHour(String key) {
        String hourString = key.substring(11, 13);
        return (Integer.parseInt(hourString) + 1) % 24;
    }
}
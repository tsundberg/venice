package se.arbetsformedlingen.venice.log;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTermsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.log.elasticsearch.ElasticSearchClient;

import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class CheckLogIT {
    private Client client;
    private ElasticSearchClient elasticSearchClient;

    @Before
    public void setUp() {
        elasticSearchClient = new ElasticSearchClient();

        Settings settings = elasticSearchClient.getSettings();
        client = elasticSearchClient.getClient(settings);
    }

    @After
    public void tearDown() {
        client.close();
    }

    @Test
    public void get_indices() throws Exception {
        printIndices();
    }

    @Test
    @Ignore
    public void get_logs_for_foretag() throws Exception {
        QueryStringQueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.foretag.*")
                .field("jboss_app_app_class")
                .analyzeWildcard(true);

        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(jboss_app_app_class)
                .execute()
                .actionGet();

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        System.out.println("Hits: " + response.getHits().getTotalHits());
    }

    @Test
    @Ignore
    public void get_calls_per_host_for_foretag() throws Exception {
        QueryStringQueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.foretag.*")
                .field("jboss_app_app_class")
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

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        for (SignificantTerms.Bucket bucket : callsPerHost.getBuckets()) {
            System.out.println(bucket.getKeyAsString() + ": " + bucket.getSubsetDf());
        }
    }

    @Test
    @Ignore
    public void get_calls_per_webservice_for_foretag() throws Exception {
        QueryStringQueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.foretag.gfrws*")
                .field("jboss_app_app_class")
                .analyzeWildcard(true);

        SignificantTermsBuilder significatTerms = AggregationBuilders
                .significantTerms("calls per webservice")
                .field("jboss_app_app_op");

        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(significatTerms)
                .execute()
                .actionGet();

        SignificantTerms callsPerHost = response.getAggregations().get("calls per webservice");

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        for (SignificantTerms.Bucket bucket : callsPerHost.getBuckets()) {
            System.out.println(bucket.getKeyAsString() + ": " + bucket.getSubsetDf());
        }
    }

    @Test
    @Ignore
    public void get_calls_per_webservice_for_cpr() throws Exception {
        QueryStringQueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.cpr*")
                .field("jboss_app_app_class")
                .analyzeWildcard(true);

        SignificantTermsBuilder significatTerms = AggregationBuilders
                .significantTerms("calls per webservice")
                .field("jboss_app_app_op");

        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(significatTerms)
                .execute()
                .actionGet();

        SignificantTerms callsPerHost = response.getAggregations().get("calls per webservice");

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        for (SignificantTerms.Bucket bucket : callsPerHost.getBuckets()) {
            System.out.println(bucket.getKeyAsString() + ": " + bucket.getSubsetDf());
        }
    }

    @Test
    @Ignore
    public void get_calls_per_webservice_for_geo() throws Exception {
        QueryStringQueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.geo*")
                .field("jboss_app_app_class")
                .analyzeWildcard(true);

        SignificantTermsBuilder significatTerms = AggregationBuilders
                .significantTerms("calls per webservice")
                .field("jboss_app_app_name");

        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(significatTerms)
                .execute()
                .actionGet();

        SignificantTerms callsPerHost = response.getAggregations().get("calls per webservice");

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        for (SignificantTerms.Bucket bucket : callsPerHost.getBuckets()) {
            System.out.println(bucket.getKeyAsString() + ": " + bucket.getSubsetDf());
        }
    }

    @Test
    @Ignore
    public void get_calls_per_hour_for_foretag() throws Exception {
        QueryStringQueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.foretag*")
                .field("jboss_app_app_class")
                .analyzeWildcard(true);

        DateHistogramBuilder histogram = AggregationBuilders
                .dateHistogram("calls per hour")
                .field("@timestamp")
                .interval(DateHistogramInterval.HOUR);

        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(jboss_app_app_class)
                .addAggregation(histogram)
                .execute()
                .actionGet();

        Histogram webbApp = response.getAggregations().get("calls per hour");

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        for (Histogram.Bucket bucket : webbApp.getBuckets()) {
            System.out.println(bucket.getKeyAsString() + ": " + bucket.getDocCount());
        }
    }

    @Test
    @Ignore
    public void get_errors_for_foretag() throws Exception {
        QueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.foretag*")
                .field("jboss_app_app_class")
                .analyzeWildcard(true);

        QueryBuilder level = queryStringQuery("ERROR")
                .defaultField("level");

        QueryBuilder query = boolQuery()
                .filter(jboss_app_app_class)
                .must(level);

        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(query)
                .execute()
                .actionGet();

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        System.out.println("Hits: " + response.getHits().getTotalHits());

        SearchHits hits = response.getHits();

        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            Object jboss_app_message = source.get("jboss_app_message");
            System.out.print(jboss_app_message);
        }
    }

    @Test
    @Ignore
    public void get_exceptions_for_foretag() throws Exception {
        QueryBuilder jboss_app_app_class = queryStringQuery("se.arbetsformedlingen.foretag*")
                .analyzeWildcard(true);

        QueryBuilder exception = queryStringQuery("Exception*")
                .analyzeWildcard(true);

        QueryBuilder query = boolQuery()
                .filter(jboss_app_app_class)
                .must(exception);

        int pageSize = 5;
        SearchResponse response = client.prepareSearch(today(), yesterday())
                .setQuery(query)
                .setSize(pageSize)
                .setScroll(TimeValue.timeValueSeconds(30))
                .execute()
                .actionGet();

        System.out.println("Response time: " + response.getTookInMillis() + " ms");
        long totalHits = response.getHits().getTotalHits();
        System.out.println("Hits: " + totalHits);

        printStacktrace(response);

        printErrorMessages(response, totalHits);
    }

    private void printStacktrace(SearchResponse response) {
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> source = hit.getSource();
            Object jboss_app_ex = source.get("jboss_app_ex");

            String stackTrace = (String) jboss_app_ex;

            String[] parts = stackTrace.split(",    ");
            String headline = parts[0];
            if (!headline.isEmpty()) {
                System.out.println(headline.substring(2));
            }

            for (int index = 1; index < parts.length; index++) {
                String part = parts[index];
                if (part.contains("arbetsformedlingen")) {
                    System.out.println("    " + part);
                }
            }
        }
    }

    private void printErrorMessages(SearchResponse response, long totalHits) {
        int page = 0;
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> source = hit.getSource();
            Object jboss_app_message = source.get("jboss_app_message");
            System.out.println(page + ": " + jboss_app_message);
            page++;
        }

        String scrollId = response.getScrollId();

        while (page < totalHits) {
            SearchResponse res = client.searchScroll(
                    client.prepareSearchScroll(scrollId)
                            .setScroll(TimeValue.timeValueSeconds(30))
                            .request()
            )
                    .actionGet();

            SearchHits hits = res.getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> source = hit.getSource();
                Object jboss_app_message = source.get("jboss_app_message");
                System.out.println(page + ": " + jboss_app_message);
                page++;
            }
        }
    }

    private String today() {
        return elasticSearchClient.today();
    }

    private String yesterday() {
        return elasticSearchClient.yesterday();
    }

    private void printIndices() {
        ClusterStateResponse response = client.admin().cluster().prepareState()
                .execute().actionGet();
        String[] indices = response.getState().getMetaData().getConcreteAllIndices();

        for (String index : indices) {
            System.out.println(index);
        }
    }
}

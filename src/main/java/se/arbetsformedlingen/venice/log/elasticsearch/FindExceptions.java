package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.ExceptionsPerTime;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeriesValue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static se.arbetsformedlingen.venice.log.elasticsearch.DateUtil.yesterday;

public class FindExceptions implements Supplier<LogResponse> {
    private Application application;

    private Map<Application, String> queryStrings = new HashMap<>();

    private Client client;

    public FindExceptions(Client client, Application application) {
        this.client = client;
        this.application = application;

        // todo config
        queryStrings.put(new Application("gfr"), "se.arbetsformedlingen.foretag*");
        queryStrings.put(new Application("geo"), "se.arbetsformedlingen.geo*");
        queryStrings.put(new Application("cpr"), "se.arbetsformedlingen.cpr*");
        queryStrings.put(new Application("agselect"), "se.arbetsformedlingen.gfr.ma*");
    }

    @Override
    public LogResponse get() {
        String queryString = queryStrings.get(application);
        QueryBuilder jboss_app_app_class = queryStringQuery(queryString)
                .analyzeWildcard(true);

        QueryBuilder exception = queryStringQuery("Exception*")
                .analyzeWildcard(true);

        QueryBuilder query = boolQuery()
                .filter(jboss_app_app_class)
                .must(exception);

        int pageSize = 5;
        SearchResponse response = client.prepareSearch(ElasticSearchClient.today(), ElasticSearchClient.yesterday())
                .setQuery(query)
                .setSize(pageSize)
                .setScroll(TimeValue.timeValueSeconds(30))
                .execute()
                .actionGet();

        return collectExceptions(response, client);
    }

    private LogResponse collectExceptions(SearchResponse response, Client client) {
        Map<String, Integer> exceptionPerHour = new HashMap<>();

        long totalHits = response.getHits().getTotalHits();
        int page = 0;
        for (SearchHit hit : response.getHits().getHits()) {
            addOneException(hit, exceptionPerHour);
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
                addOneException(hit, exceptionPerHour);
                page++;
            }
        }

        LogType logType = new LogType("exception");
        List<TimeSeriesValue> timeSeriesValues = new LinkedList<>();

        for (String key : exceptionPerHour.keySet()) {
            Integer value = exceptionPerHour.get(key);
            timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse(key), value));
        }

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application, timeSeriesValues);

        return new LogResponse(application, logType, exceptionsPerTime);
    }

    private void addOneException(SearchHit hit, Map<String, Integer> exceptionPerHour) {
        // this might a too complicated solution. ES should be able to group the data per hour. This is the way it probably is implemented in other searches. Check if that is the case when you see this comment.
        Map<String, Object> source = hit.getSource();
        String timeStamp = (String) source.get("@timestamp");

        LocalDateTime eventTime = ElasticSearchClient.getDate(timeStamp);

        if (eventTime.isAfter(yesterday())) {
            String key = eventTime.toString();
            Integer hits = exceptionPerHour.get(key);

            if (hits == null) {
                hits = 1;
            } else {
                hits = hits + 1;
            }

            exceptionPerHour.put(key, hits);
        }
    }
}

package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
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

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class FindExceptions extends ElasticSearchClient implements java.util.function.Supplier<LogResponse> {
    private Application application;

    private Map<Application, String> queryStrings = new HashMap<>();

    public FindExceptions(Application application) {
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

        return collectExceptions(response, client);
    }

    private LogResponse collectExceptions(SearchResponse response, Client client) {
        Map<Integer, Integer> exceptionPerHour = new HashMap<>();

        initiate(exceptionPerHour);

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

        for (Integer key : exceptionPerHour.keySet()) {
            Integer value = exceptionPerHour.get(key);
            timeSeriesValues.add(new TimeSeriesValue(key, value));
        }

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application, timeSeriesValues);

        return new LogResponse(application, logType, exceptionsPerTime);
    }

    private void initiate(Map<Integer, Integer> exceptionPerHour) {
        for (int key = 0; key < 24; key++) {
            exceptionPerHour.put(key, 0);
        }
    }

    private void addOneException(SearchHit hit, Map<Integer, Integer> exceptionPerHour) {
        Map<String, Object> source = hit.getSource();
        String timeStamp = (String) source.get("@timestamp");

        LocalDateTime localDateTime = getDate(timeStamp);

        int hour = localDateTime.getHour();
        Integer hits = exceptionPerHour.get(hour);

        if (hits == null) {
            hits = 1;
        } else {
            hits = hits + 1;
        }
        exceptionPerHour.put(hour, hits);
    }

}

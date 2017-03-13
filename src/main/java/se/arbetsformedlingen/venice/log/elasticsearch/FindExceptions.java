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

    @Override
    public LogResponse get() {
        Settings settings = getSettings();

        Client client = getClient(settings);

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

        LogResponse logResponse = collectExceptions(response, client);

        System.out.println("Application: " + logResponse.getApplication());
        for (TimeSeriesValue tsv : logResponse.getExceptionPerHour()) {
            System.out.println("  " + tsv.getTime() + ": " + tsv.getValue());
        }
        System.out.println();
        System.out.println();

        return logResponse;
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

        Application application = new Application("gfr");
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

    LocalDateTime getDate(String timeStamp) {
        String yearString = timeStamp.substring(0, 4);
        int year = Integer.parseInt(yearString);

        String monthString = timeStamp.substring(5, 7);
        int month = Integer.parseInt(monthString);

        String dayString = timeStamp.substring(8, 10);
        int day = Integer.parseInt(dayString);

        String hourString = timeStamp.substring(11, 13);
        int hour = Integer.parseInt(hourString);

        String minuteString = timeStamp.substring(14, 16);
        int minute = Integer.parseInt(minuteString);

        String secondString = timeStamp.substring(17, 19);
        int second = Integer.parseInt(secondString);

        return convertToCET(LocalDateTime.of(year, month, day, hour, minute, second));
    }

    private LocalDateTime convertToCET(LocalDateTime localDateTime) {
        return localDateTime.plusHours(1);
    }
}

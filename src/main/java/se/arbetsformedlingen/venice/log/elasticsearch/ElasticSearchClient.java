package se.arbetsformedlingen.venice.log.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ElasticSearchClient {
    public static Client getClient(Settings settings) {
        return new TransportClient.Builder()
                .settings(settings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("elk.arbetsformedlingen.se", 9300)));
    }

    public static Settings getSettings() {
        return Settings.settingsBuilder()
                .put("cluster.name", "logsys_prod")
                .build();
    }

    public static String today() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        String month = "" + today.getMonthValue();
        month = padWithLeadinZero(month);

        String day = "" + today.getDayOfMonth();
        day = padWithLeadinZero(day);

        return getIndex(year, month, day);
    }

    public static String yesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        int year = yesterday.getYear();

        String month = "" + yesterday.getMonthValue();
        month = padWithLeadinZero(month);

        String day = "" + yesterday.getDayOfMonth();
        day = padWithLeadinZero(day);

        return getIndex(year, month, day);
    }

    private static String getIndex(int year, String month, String day) {
        return "logstash-" + year + "." + month + "." + day;
    }

    private static String padWithLeadinZero(String tooShort) {
        StringBuilder tooShortBuilder = new StringBuilder(tooShort);
        while (tooShortBuilder.length() < 2) {
            tooShortBuilder.insert(0, "0");
        }
        tooShort = tooShortBuilder.toString();
        return tooShort;
    }
}

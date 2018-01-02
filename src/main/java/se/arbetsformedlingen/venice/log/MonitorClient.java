package se.arbetsformedlingen.venice.log;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MonitorClient
{
    private String format;
    private String[] hosts;
    private int port;
    private String[] versions;

    public MonitorClient(String format, String[] hosts, int port, String[] versions) {
        this.format = format;
        this.hosts = hosts;
        this.port = port;
        this.versions = versions;
    }

    public MonitorResult fetch() throws IOException {
        Executor executor = Executor.newInstance();

        List<LogEntry> entries = new ArrayList<>();
        for (String host : hosts) {
            for (String version : versions) {
                String url = format.replaceAll("\\$\\{host\\}", host)
                                   .replaceAll("\\$\\{port\\}", String.valueOf(port))
                                   .replaceAll("\\$\\{version\\}", version);
                Request request = Request.Get(url);

                String rawJson = executor
                    .execute(request)
                    .returnContent()
                    .asString();

                JSONArray arr = new JSONArray(rawJson);
                entries.addAll(jsonArrayToLogEntries(host, version, arr));
            }
        }

        return new MonitorResult(entries);
    }

    public List<LogEntry> jsonArrayToLogEntries(String host,
                                                String version,
                                                JSONArray arr) {
        List<LogEntry> entries = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            String service = obj.getString("service");
            String method = obj.getString("method");
            String applicationId = obj.getString("applicationId");
            int count = obj.getInt("count");
            double avg = obj.getDouble("avg");
            double max = obj.getDouble("max");
            double p95 = obj.getDouble("p95");
            double p99 = obj.getDouble("p99");

            JSONArray buckets = obj.getJSONArray("buckets");
            List<CallsPerHour> hours = jsonArrayToCallsPerHour(buckets);

            entries.add(new LogEntry(host,
                                     version,
                                     service,
                                     method,
                                     applicationId,
                                     count,
                                     avg,
                                     max,
                                     p95,
                                     p99,
                                     hours));
        }

        return entries;
    }

    public List<CallsPerHour> jsonArrayToCallsPerHour(JSONArray arr) {
        List<CallsPerHour> hours = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            String rawDate = obj.getString("date");
            LocalDate date = LocalDate.parse(rawDate);
            if (obj.getInt("hour") == 24) {
                date = date.plusDays(1);
            }

            int hour = obj.getInt("hour") % 24;
            int calls = obj.getInt("value");

            hours.add(new CallsPerHour(date, hour, calls));
        }

        return hours;
    }
}

package se.arbetsformedlingen.venice.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import se.arbetsformedlingen.venice.model.*;

public class MonitorResult
{
    private List<LogEntry> entries;

    public MonitorResult(List<LogEntry> entries) {
        this.entries = entries;
    }

    public int getEntryCount() {
        return entries.size();
    }

    public List<HostValue> getHostLoad() {

        Map<String, List<LogEntry>> hostGroups = entries.stream()
            .collect(Collectors.groupingBy(LogEntry::getHost));

        List<HostValue> result = new ArrayList<>();
        for (String hostStr : hostGroups.keySet()) {
            Host host = new Host(hostStr);

            List<LogEntry> hostEntries = hostGroups.get(hostStr);
            int total = 0;
            for (LogEntry entry : hostEntries) {
                total += entry.getCount();
            }

            result.add(new HostValue(host, total));
        }

        return result;
    }

    public List<ConsumingSystemValue> getConsumingSystemLoad() {

        Map<String, List<LogEntry>> consumerGroups = entries.stream()
            .collect(Collectors.groupingBy(LogEntry::getConsumer));

        List<ConsumingSystemValue> result = new ArrayList<>();
        LocalDateTime lastTwentyFourHours = LocalDateTime.now().minusHours(24);
        for (String consumer : consumerGroups.keySet()) {
            ConsumingSystem consumingSystem = new ConsumingSystem(consumer);

            List<LogEntry> consumerEntries = consumerGroups.get(consumer);
            List<CallsPerHour> totalHours = new ArrayList<>();
            for (LogEntry entry : consumerEntries) {
                totalHours.addAll(entry.getHours()
                        .stream()
                        .filter(hour -> hour.getDateTime().isAfter(lastTwentyFourHours))
                        .collect(Collectors.toList()));
            }

            result.addAll(groupCallsPerHour(consumingSystem, totalHours));
        }

        return result;
    }

    private List<ConsumingSystemValue> groupCallsPerHour(ConsumingSystem consumingSystem, 
                                                         List<CallsPerHour> values) {
        List<ConsumingSystemValue> result = new ArrayList<>();

        values.stream()
            .collect(Collectors.groupingBy(CallsPerHour::getDateTime))
            .forEach((datetime, valuesAtHour) -> {
                int total = 0;
                for (CallsPerHour hour : valuesAtHour) {
                    total += hour.getCalls();
                }

                TimeSeriesValue value = new TimeSeriesValue(datetime, total);
                result.add(new ConsumingSystemValue(consumingSystem, value));
            });

        return result;
    }
}

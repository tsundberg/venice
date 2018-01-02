package se.arbetsformedlingen.venice.log;

import java.util.List;

public class LogEntry
{
    private String host;
    private String version;
    private String service;
    private String method;
    private String consumer;
    private int count;
    private double avg;
    private double max;
    private double p95;
    private double p99;
    private List<CallsPerHour> hours;

    public LogEntry(String host,
                    String version,
                    String service,
                    String method,
                    String consumer,
                    int count,
                    double avg,
                    double max,
                    double p95,
                    double p99,
                    List<CallsPerHour> hours) {
        this.host = host;
        this.version = version;
        this.service = service;
        this.method = method;
        this.consumer = consumer;
        this.count = count;
        this.avg = avg;
        this.max = max;
        this.p95 = p95;
        this.p99 = p99;
        this.hours = hours;
    }

    public String getHost() { return host; }
    public String getVersion() { return version; }
    public String getService() { return service; }
    public String getMethod() { return method; }
    public String getConsumer() { return consumer; }
    public int getCount() { return count; }
    public double getAvg() { return avg; }
    public double getMax() { return max; }
    public double getP95() { return p95; }
    public double getP99() { return p99; }
    public List<CallsPerHour> getHours() { return hours; }

}

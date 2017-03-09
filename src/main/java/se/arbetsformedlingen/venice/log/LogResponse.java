package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.model.*;

import java.util.List;

public class LogResponse {
    private Application application;
    private LogType logType;
    private TimeSeries timeSeries;
    private ApplicationSeries applicationSeries;

    public LogResponse(Application application, LogType logType, TimeSeries timeSeries) {
        this.application = application;
        this.logType = logType;
        this.timeSeries = timeSeries;
    }

    public LogResponse(Application application, LogType logType, ApplicationSeries series) {
        this.application = application;
        this.logType = logType;
        applicationSeries = series;
    }

    public Application getApplication() {
        return application;
    }

    public String getLogTypeName() {
        return logType.getType();
    }

    LogType getLogType() {
        return logType;
    }

    List<TimeValue> getTimeValues() {
        return timeSeries.getTimeValues();
    }

    public List<ApplicationLoad> getApplicationLoadValues() {
        return applicationSeries.getLoadValues();
    }
}

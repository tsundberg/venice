package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.model.*;

import java.util.List;

public class LogResponse {
    private Application application;
    private LogType logType;
    private TimeSeries timeSeries;
    private ApplicationLoad applicationLoad;
    private WebserviceLoad webserviceLoad;

    LogResponse(Application application, LogType logType, TimeSeries timeSeries) {
        this.application = application;
        this.logType = logType;
        this.timeSeries = timeSeries;
    }

    LogResponse(Application application, LogType logType, ApplicationLoad webserviceLoad) {
        this.application = application;
        this.logType = logType;
        applicationLoad = webserviceLoad;
    }

    LogResponse(Application application, LogType logType, WebserviceLoad webserviceLoad) {
        this.application = application;
        this.logType = logType;
        this.webserviceLoad = webserviceLoad;
    }

    public Application getApplication() {
        return application;
    }

    String getLogTypeName() {
        return logType.getType();
    }

    LogType getLogType() {
        return logType;
    }

    List<TimeSeriesValue> getTimeValues() {
        return timeSeries.getTimeValues();
    }

    List<HostLoadValue> getApplicationLoadValues() {
        return applicationLoad.getLoadValues();
    }

    List<WebserviceLoadValue> getWebserviceLoadValues() {
        return webserviceLoad.getLoadValues();
    }
}

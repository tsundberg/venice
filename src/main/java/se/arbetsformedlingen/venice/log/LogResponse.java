package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.*;

import java.util.List;

public class LogResponse {
    private Application application;
    private LogType logType;

    private ConsumingSystemLoad consumingSystemLoad = new ConsumingSystemLoad(application);
    private TimeSeries timeSeries = new TimeSeries();
    private ApplicationLoad applicationLoad = new ApplicationLoad(application);
    private WebserviceLoad webserviceLoad = new WebserviceLoad(application);

    LogResponse(Application application, LogType logType) {
        this.application = application;
        this.logType = logType;
    }

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

    LogResponse(Application application, LogType logType, ConsumingSystemLoad consumingSystemLoad) {
        this.application = application;
        this.logType = logType;
        this.consumingSystemLoad = consumingSystemLoad;
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

    List<HostValue> getApplicationLoadValues() {
        return applicationLoad.getLoadValues();
    }

    List<WebserviceValue> getWebserviceLoadValues() {
        return webserviceLoad.getLoadValues();
    }

    List<ConsumingSystemValue> getConsumingSystemValues() {
        return consumingSystemLoad.getConsumingSystems();
    }
}

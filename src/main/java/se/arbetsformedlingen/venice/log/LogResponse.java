package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.model.*;

import java.util.Collections;
import java.util.List;

public class LogResponse {
    private Application application;
    private LogType logType;

    private ConsumingSystemLoad consumingSystemLoad = new ConsumingSystemLoad(application, Collections.emptyList());
    private ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application);
    private ApplicationLoad applicationLoad = new ApplicationLoad();
    private WebserviceLoad webserviceLoad = new WebserviceLoad();

    LogResponse(Application application, LogType logType) {
        this.application = application;
        this.logType = logType;
    }

    public LogResponse(Application application, LogType logType, ExceptionsPerTime exceptionsPerTime) {
        this.application = application;
        this.logType = logType;
        this.exceptionsPerTime = exceptionsPerTime;
    }

    public LogResponse(Application application, LogType logType, ApplicationLoad applicationLoad) {
        this.application = application;
        this.logType = logType;
        this.applicationLoad = applicationLoad;
    }

    public LogResponse(Application application, LogType logType, WebserviceLoad webserviceLoad) {
        this.application = application;
        this.logType = logType;
        this.webserviceLoad = webserviceLoad;
    }

    public LogResponse(Application application, LogType logType, ConsumingSystemLoad consumingSystemLoad) {
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

    public LogType getLogType() {
        return logType;
    }

    List<TimeSeriesValue> getExceptionPerHour() {
        return exceptionsPerTime.getTimeValues();
    }

    public List<HostValue> getApplicationLoadValues() {
        return applicationLoad.getLoadValues();
    }

    List<WebserviceValue> getWebserviceLoadValues() {
        return webserviceLoad.getLoadValues();
    }

    List<ConsumingSystemValue> getConsumingSystemValues() {
        return consumingSystemLoad.getConsumingSystems();
    }
}

package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeries;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class LogResponse {
    private Application application;
    private LogType logType;
    private TimeSeries timeSeries;

    public LogResponse(Application application, LogType logType, TimeSeries timeSeries) {
        this.application = application;
        this.logType = logType;
        this.timeSeries = timeSeries;
    }

    public Application getApplication() {
        return application;
    }

    public LogType getLogType() {
        return logType;
    }
}

package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.model.LogType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LatestLogs {
    private static ConcurrentMap<String, LogResponse> logResponse = new ConcurrentHashMap<>();

    void addLog(LogResponse value) {
        String key = generateKey(value.getApplication(), value.getLogType());
        logResponse.put(key, value);
    }

    LogResponse getLog(Application application, LogType logType) {
        String key = generateKey(application, logType);
        return logResponse.get(key);
    }

    private String generateKey(Application application, LogType logType) {
        return application + "->" + logType;
    }
}

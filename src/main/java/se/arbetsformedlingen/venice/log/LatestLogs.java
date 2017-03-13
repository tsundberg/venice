package se.arbetsformedlingen.venice.log;

import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.LogType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class LatestLogs {
    private static ConcurrentMap<String, LogResponse> logResponse = new ConcurrentHashMap<>();

    void addLog(LogResponse value) {
        String key = generateKey(value.getApplication(), value.getLogType());
        logResponse.put(key, value);
    }

    LogResponse getLog(Application application, LogType logType) {
        String key = generateKey(application, logType);

        if (logResponse.containsKey(key)) {
            return logResponse.get(key);
        } else {
            return new LogResponse(application, logType);
        }
    }

    private String generateKey(Application application, LogType logType) {
        return application + "->" + logType;
    }

    static void clearRepository() {
        logResponse = new ConcurrentHashMap<>();
    }
}

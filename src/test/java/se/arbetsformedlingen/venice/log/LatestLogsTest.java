package se.arbetsformedlingen.venice.log;

import org.junit.Before;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.ExceptionsPerTime;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeriesValue;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LatestLogsTest {

    @Before
    public void setUp() {
        LatestLogs.clearRepository();
    }

    @Test
    public void add_exception_log_entry() {
        LatestLogs latestLog = new LatestLogs();

        Application application = new Application("gfr");
        LogType logType = new LogType("exception");

        List<TimeSeriesValue> timeSeriesValues = new LinkedList<>();
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T10:50"), 17));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T11:17"), 42));
        timeSeriesValues.add(new TimeSeriesValue(LocalDateTime.parse("2017-09-21T09:42"), 4711));

        ExceptionsPerTime exceptionsPerTime = new ExceptionsPerTime(application, timeSeriesValues);
        LogResponse logEntry = new LogResponse(application, logType, exceptionsPerTime);

        latestLog.addLog(logEntry);

        LogResponse actual = latestLog.getLog(application, logType);

        assertThat(actual.getApplication()).isEqualTo(new Application("gfr"));
        assertThat(actual.getLogType()).isEqualTo(new LogType("exception"));
    }

    @Test
    public void never_return_null_for_consuming_system_load() {
        LatestLogs latestLog = new LatestLogs();

        Application application = new Application("gfr");
        LogType logType = new LogType("consuming-system");

        LogResponse actual = latestLog.getLog(application, logType);

        assertThat(actual.getApplication()).isEqualTo(new Application("gfr"));
        assertThat(actual.getLogType()).isEqualTo(new LogType("consuming-system"));
        assertThat(actual.getConsumingSystemValues()).isEmpty();
    }

    @Test
    public void never_return_null_for_time_series() {
        LatestLogs latestLog = new LatestLogs();

        Application application = new Application("gfr");
        LogType logType = new LogType("exception");

        LogResponse actual = latestLog.getLog(application, logType);

        assertThat(actual.getApplication()).isEqualTo(new Application("gfr"));
        assertThat(actual.getLogType()).isEqualTo(new LogType("exception"));
        assertThat(actual.getExceptionPerHour()).isEmpty();
    }

    @Test
    public void never_return_null_for_application_load() {
        LatestLogs latestLog = new LatestLogs();

        Application application = new Application("gfr");
        LogType logType = new LogType("application-load");

        LogResponse actual = latestLog.getLog(application, logType);

        assertThat(actual.getApplication()).isEqualTo(new Application("gfr"));
        assertThat(actual.getLogType()).isEqualTo(new LogType("application-load"));
        assertThat(actual.getApplicationLoadValues()).isEmpty();
    }

    @Test
    public void never_return_null_for_webservice_load() {
        LatestLogs latestLog = new LatestLogs();

        Application application = new Application("gfr");
        LogType logType = new LogType("webservice-load");

        LogResponse actual = latestLog.getLog(application, logType);

        assertThat(actual.getApplication()).isEqualTo(new Application("gfr"));
        assertThat(actual.getLogType()).isEqualTo(new LogType("webservice-load"));
        assertThat(actual.getWebserviceLoadValues()).isEmpty();
    }
}

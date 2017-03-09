package se.arbetsformedlingen.venice.log;

import org.junit.Test;
import se.arbetsformedlingen.venice.common.Application;
import se.arbetsformedlingen.venice.model.LogType;
import se.arbetsformedlingen.venice.model.TimeSeries;
import se.arbetsformedlingen.venice.model.TimeSeriesValue;

import static org.assertj.core.api.Assertions.assertThat;

public class LatestLogsTest {

    @Test
    public void add_exception_log_entry() {
        LatestLogs latestLog = new LatestLogs();

        Application application = new Application("gfr");
        LogType logType = new LogType("exception");
        TimeSeries timeSeries = new TimeSeries(new TimeSeriesValue(10, 17), new TimeSeriesValue(11, 42), new TimeSeriesValue(9, 4711));
        LogResponse logEntry = new LogResponse(application, logType, timeSeries);

        latestLog.addLog(logEntry);

        LogResponse actual = latestLog.getLog(application, logType);

        assertThat(actual.getApplication()).isEqualTo(new Application("gfr"));
        assertThat(actual.getLogType()).isEqualTo(new LogType("exception"));
    }

    // todo never return null

}

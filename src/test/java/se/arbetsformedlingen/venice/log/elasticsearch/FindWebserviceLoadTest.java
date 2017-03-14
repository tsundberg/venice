package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;

public class FindWebserviceLoadTest {
    @Test
    @Ignore
    public void find_application_load() {
        FindWebserviceLoad findWebserviceLoad = new FindWebserviceLoad(new Application("cpr"));
        LogResponse logResponse = findWebserviceLoad.get();

        findWebserviceLoad = new FindWebserviceLoad(new Application("gfr"));
        logResponse = findWebserviceLoad.get();

        findWebserviceLoad = new FindWebserviceLoad(new Application("geo"));
        logResponse = findWebserviceLoad.get();
    }
}

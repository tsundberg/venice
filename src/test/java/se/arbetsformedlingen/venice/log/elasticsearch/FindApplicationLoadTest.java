package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.log.LogResponse;
import se.arbetsformedlingen.venice.model.Application;

public class FindApplicationLoadTest {
    @Test
    @Ignore
    public void find_application_load() {
        FindApplicationLoad findExceptions = new FindApplicationLoad(new Application("gfr"));
        LogResponse logResponse = findExceptions.get();

        System.out.println(logResponse);
    }
}

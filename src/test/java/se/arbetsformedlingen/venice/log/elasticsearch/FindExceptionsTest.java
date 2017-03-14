package se.arbetsformedlingen.venice.log.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class FindExceptionsTest {
    @Test
    @Ignore
    public void find_exceptions() {
        FindExceptions findExceptions = new FindExceptions(new Application("geo"));
        findExceptions.get();
    }
}

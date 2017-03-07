package se.arbetsformedlingen.venice.tpjadmin;

import org.junit.Ignore;
import org.junit.Test;
import se.arbetsformedlingen.venice.common.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TPJAdminIT {

    @Test
    @Ignore
    public void get_servers() throws Exception {
        Server expected = new Server(new Application("gfr"), new Environment("u1"), new Host("l7700649.u1.local"), new Port("8580"));

        List<Server> actual = TPJAdmin.getServers();

        assertThat(actual).contains(expected);
    }
}
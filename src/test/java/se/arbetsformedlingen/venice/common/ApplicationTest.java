package se.arbetsformedlingen.venice.common;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ApplicationTest {

    @Test
    public void get_gfr_probename(){
        String expected = "UgkForetagProbe";

        Application app = new Application("gfr");

        String actual = app.getProbeName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void get_gfr_port(){
        String expected = "/wildfly05";

        Application app = new Application("gfr");

        String actual = app.getPort();

        assertThat(actual).isEqualTo(expected);
    }
}

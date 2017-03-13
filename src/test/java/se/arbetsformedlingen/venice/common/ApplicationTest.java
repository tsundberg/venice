package se.arbetsformedlingen.venice.common;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ApplicationTest {

    @Test
    public void get_gfr_probename() {
        String expected = "UgkForetagProbe";

        Application app = new Application("gfr");

        String actual = app.getProbeName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void get_agselect_probename() {
        String expected = "MarknadsanalysProbe";

        Application app = new Application("agselect");

        String actual = app.getProbeName();

        assertThat(actual).isEqualTo(expected);
    }
}

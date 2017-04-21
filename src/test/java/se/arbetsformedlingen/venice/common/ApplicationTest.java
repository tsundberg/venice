package se.arbetsformedlingen.venice.common;

import org.junit.Test;
import se.arbetsformedlingen.venice.model.Application;
import se.arbetsformedlingen.venice.model.Probe;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ApplicationTest {

    @Test
    public void get_gfr_probe_name() {
        Probe probe = new Probe("UgkForetagProbe");
        Application app = new Application("gfr", probe);

        Probe actual = app.getProbe();

        assertThat(actual).isEqualTo(new Probe("UgkForetagProbe"));
    }

    @Test
    public void get_agselect_probe_name() {
        Probe probe = new Probe("MarknadsanalysProbe");
        Application app = new Application("agselect", probe);

        Probe actual = app.getProbe();

        assertThat(actual).isEqualTo(new Probe("MarknadsanalysProbe"));
    }

    @Test
    public void get_geo_probe_name() {
        Probe probe = new Probe("UgkGeoProbe");
        Application app = new Application("geo", probe);

        Probe actual = app.getProbe();

        assertThat(actual).isEqualTo(new Probe("UgkGeoProbe"));
    }

    @Test
    public void get_cpr_probe_name() {
        Probe probe = new Probe("CprProbe");
        Application app = new Application("cpr", probe);

        Probe actual = app.getProbe();

        assertThat(actual).isEqualTo(new Probe("CprProbe"));
    }
}

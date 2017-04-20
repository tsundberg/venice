package se.arbetsformedlingen.venice.ci;

import org.junit.Test;
import se.arbetsformedlingen.venice.configuration.Configuration;
import se.arbetsformedlingen.venice.tpjadmin.TPJAdmin;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckBuildTest {
    @Test
    public void build_error_response() {
        Configuration configuration = new Configuration("build/resources/main/configuration.yaml");
        TPJAdmin tpjAdmin = new TPJAdmin(configuration);
        CheckBuild checkBuild = new CheckBuild(tpjAdmin);

        Exception sample = new Exception("Error message");

        List<BuildResponse> actual = checkBuild.errorResponse(sample);

        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getStatus()).isEqualTo("Error message");
    }
}

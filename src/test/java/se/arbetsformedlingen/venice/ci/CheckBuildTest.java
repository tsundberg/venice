package se.arbetsformedlingen.venice.ci;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckBuildTest {
    @Test
    public void build_error_response (){
        CheckBuild checkBuild = new CheckBuild();

        Exception sample = new Exception("Error message");

        List<BuildResponse> actual = checkBuild.errorResponse(sample);

        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getStatus()).isEqualTo("Error message");
    }
}

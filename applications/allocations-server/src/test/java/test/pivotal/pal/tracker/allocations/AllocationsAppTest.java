package test.pivotal.pal.tracker.allocations;

import io.pivotal.pal.tracker.allocations.App;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class AllocationsAppTest {

    @Test
    public void embedded() {
        System.setProperty("eureka.client.enabled", "false");

        App.main(new String[]{});

        String response = new RestTemplate().getForObject("http://localhost:8181/allocations?projectId=0", String.class);

        assertThat(response).isEqualTo("[]");
    }
}

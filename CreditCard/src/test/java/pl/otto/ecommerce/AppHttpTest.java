package pl.otto.ecommerce;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AppHttpTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate http;
    @Test
    void itLoadsEcommerceHomepage() {
        var url = String.format("http://localhost:%s", port);

        ResponseEntity<String> response = http.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Welcome to My Ecommerce");
    }
}

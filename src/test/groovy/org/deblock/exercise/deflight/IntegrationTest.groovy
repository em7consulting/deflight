package org.deblock.exercise.deflight

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IntegrationTest extends Specification {

    public static WireMockServer wm;

    public static MultiValueMap<String,String> headers = new LinkedMultiValueMap<>()

    @Autowired
    TestRestTemplate restTemplate

    @LocalServerPort
    int port;

    static {
        wm = new WireMockServer();
        wm.start();
        configureFor("localhost", 8080);

        headers.add("Content-Type", "application/json")
        headers.add("Accept", "application/json")
    }
}

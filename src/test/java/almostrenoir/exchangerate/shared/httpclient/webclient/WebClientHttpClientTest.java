package almostrenoir.exchangerate.shared.httpclient.webclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.Getter;
import org.junit.jupiter.api.*;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

import almostrenoir.exchangerate.shared.httpclient.HttpException;

class WebClientHttpClientTest {

    private static final int WIREMOCK_PORT = 8092;
    private static final String WIREMOCK_URL = "http://localhost:" + WIREMOCK_PORT;

    private static WebClientHttpClient httpClient;
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        httpClient = new WebClientHttpClient();
        wireMockServer = new WireMockServer(WIREMOCK_PORT);
        wireMockServer.start();
        configureFor(WIREMOCK_PORT);
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void shouldReturnMappedBodyOnSuccessfulResponse() {
        String responseBody = "{\"name\":\"Foo\", \"age\":35}";
        stubFor(get(urlEqualTo("/success"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        Mono<TestResponse> responseMono = httpClient.get(WIREMOCK_URL + "/success", TestResponse.class, 1500);
        TestResponse response = responseMono.block();

        assertEquals("Foo", response.getName());
        assertEquals(35, response.getAge());
    }

    @Test
    public void shouldThrowExceptionIfResourceNotFound() {
        stubFor(get(urlEqualTo("/not-found")).willReturn(aResponse().withStatus(404)));

        assertThrows(HttpException.NotFound.class,
                () -> httpClient.get(WIREMOCK_URL + "/not-found", TestResponse.class, 1500).block());
    }

    @Test
    public void shouldThrowExceptionIfServerError() {
        stubFor(get(urlEqualTo("/server-error")).willReturn(aResponse().withStatus(500)));

        assertThrows(HttpException.ServerError.class,
                () -> httpClient.get(WIREMOCK_URL + "/server-error", TestResponse.class, 1500).block());
    }

    @Test
    public void shouldThrowExceptionIfTimeoutExceeded() {
        stubFor(get(urlEqualTo("/timeout"))
                .willReturn(aResponse()
                        .withFixedDelay(5000)
                        .withStatus(200)));

        assertThrows(HttpException.TimeoutExceeded.class,
                () -> httpClient.get(WIREMOCK_URL + "/timeout", TestResponse.class, 100).block());
    }

    @Getter
    private static class TestResponse {
        private String name;
        private int age;
    }

}
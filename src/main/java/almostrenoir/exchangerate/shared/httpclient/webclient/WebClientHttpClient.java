package almostrenoir.exchangerate.shared.httpclient.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

import almostrenoir.exchangerate.shared.httpclient.HttpClient;
import almostrenoir.exchangerate.shared.httpclient.HttpException;

@Component
public class WebClientHttpClient implements HttpClient {

    private final WebClient webClient;

    @Autowired
    public WebClientHttpClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public <T> Mono<T> get(String url, Class<T> responseType, int timeout) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(new HttpException.NotFound())
                ).onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new HttpException.ServerError())
                ).bodyToMono(responseType)
                .timeout(Duration.ofMillis(timeout))
                .onErrorMap(TimeoutException.class, ex -> new HttpException.TimeoutExceeded());
    }
}

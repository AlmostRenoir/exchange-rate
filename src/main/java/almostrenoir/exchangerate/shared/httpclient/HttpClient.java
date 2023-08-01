package almostrenoir.exchangerate.shared.httpclient;

import reactor.core.publisher.Mono;

public interface HttpClient {
    <T> Mono<T> get(String url, Class<T> responseType, int timeout);
}

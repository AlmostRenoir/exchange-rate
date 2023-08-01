package almostrenoir.exchangerate.shared.httpclient;

public class HttpException {
    public static class NotFound extends RuntimeException {}
    public static class ServerError extends RuntimeException {}
    public static class TimeoutExceeded extends RuntimeException {}
}

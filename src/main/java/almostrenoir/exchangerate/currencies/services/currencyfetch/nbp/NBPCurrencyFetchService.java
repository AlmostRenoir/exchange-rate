package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.services.currencyfetch.CurrencyFetchService;
import almostrenoir.exchangerate.shared.exceptions.DataNotFoundException;
import almostrenoir.exchangerate.shared.exceptions.ExternalServiceException;
import almostrenoir.exchangerate.shared.httpclient.HttpClient;
import almostrenoir.exchangerate.shared.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class NBPCurrencyFetchService implements CurrencyFetchService {

    private static final String CURRENT_CURRENCY_URL = "https://api.nbp.pl/api/exchangerates/rates/A/%s/?format=json";

    private final HttpClient httpClient;

    @Autowired
    public NBPCurrencyFetchService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<BigDecimal> getCurrentCurrencyValue(CurrencyFetchIncomingDTO currencyFetchIncomingDTO) {
        String url = String.format(CURRENT_CURRENCY_URL, currencyFetchIncomingDTO.getCurrency());
        return httpClient.get(url, NBPTableRecord.class, 5000)
                .onErrorMap(HttpException.NotFound.class, ex -> new DataNotFoundException("There is no available exchange rate for given currency"))
                .onErrorMap(HttpException.ServerError.class, this::returnExternalServiceException)
                .onErrorMap(HttpException.TimeoutExceeded.class, this::returnExternalServiceException)
                .map(table -> table.getRates().get(0).getMid());
    }

    private ExternalServiceException returnExternalServiceException(Throwable throwable) {
        return new ExternalServiceException("NBP service is currently unavailable");
    }
}

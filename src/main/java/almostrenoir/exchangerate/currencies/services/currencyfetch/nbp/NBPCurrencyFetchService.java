package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.request.repository.NewCurrencyRequest;
import almostrenoir.exchangerate.currencies.services.currencyfetch.CurrencyFetchService;
import almostrenoir.exchangerate.shared.exceptions.DataNotFoundException;
import almostrenoir.exchangerate.shared.exceptions.ExternalServiceException;
import almostrenoir.exchangerate.shared.httpclient.HttpClient;
import almostrenoir.exchangerate.shared.httpclient.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
public class NBPCurrencyFetchService implements CurrencyFetchService {

    private static final String CURRENT_CURRENCY_URL = "https://api.nbp.pl/api/exchangerates/rates/A/%s/?format=json";
    private static final int CURRENT_CURRENCY_TIMEOUT = 5000;

    private final HttpClient httpClient;
    private final CurrencyRequestRepository currencyRequestRepository;

    @Autowired
    public NBPCurrencyFetchService(HttpClient httpClient, CurrencyRequestRepository currencyRequestRepository) {
        this.httpClient = httpClient;
        this.currencyRequestRepository = currencyRequestRepository;
    }

    @Override
    public Mono<BigDecimal> getCurrentCurrencyValue(CurrencyFetchIncomingDTO currencyFetchIncomingDTO) {
        String url = String.format(CURRENT_CURRENCY_URL, currencyFetchIncomingDTO.getCurrency());
        return httpClient.get(url, NBPTableRecord.class, CURRENT_CURRENCY_TIMEOUT)
                .onErrorMap(HttpException.NotFound.class, ex -> new DataNotFoundException("There is no available exchange rate for given currency"))
                .onErrorMap(HttpException.ServerError.class, this::onCurrentCurrencyServerError)
                .onErrorMap(HttpException.TimeoutExceeded.class, this::onCurrentCurrencyTimeout)
                .map(tableRecord -> processTableRecord(tableRecord, currencyFetchIncomingDTO));
    }

    private ExternalServiceException onCurrentCurrencyServerError(Throwable throwable) {
        log.warn("NBP service returned with server error when fetching current currency");
        return new ExternalServiceException("NBP service is currently unavailable");
    }

    private ExternalServiceException onCurrentCurrencyTimeout(Throwable throwable) {
        log.warn("NBP service exceeded timeout ({} ms) when fetching current currency", CURRENT_CURRENCY_TIMEOUT);
        return new ExternalServiceException("NBP service is currently unavailable");
    }

    private BigDecimal processTableRecord(NBPTableRecord tableRecord, CurrencyFetchIncomingDTO currencyFetchIncomingDTO) {
        BigDecimal result = tableRecord.getRates().get(0).getMid();
        NewCurrencyRequest newCurrencyRequest = new NewCurrencyRequest(
                currencyFetchIncomingDTO.getName(),
                currencyFetchIncomingDTO.getCurrency(),
                result
        );
        currencyRequestRepository.add(newCurrencyRequest);
        return result;
    }
}

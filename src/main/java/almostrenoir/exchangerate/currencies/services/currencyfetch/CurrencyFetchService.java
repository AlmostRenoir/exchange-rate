package almostrenoir.exchangerate.currencies.services.currencyfetch;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface CurrencyFetchService {
    Mono<BigDecimal> getCurrentCurrencyValue(CurrencyFetchIncomingDTO currencyFetchIncomingDTO);
}

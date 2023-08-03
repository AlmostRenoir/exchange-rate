package almostrenoir.exchangerate.currencies.services.main;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CurrenciesMainService {
    Mono<CurrencyFetchOutgoingDTO> getCurrentCurrencyValue(CurrencyFetchIncomingDTO currencyFetchIncomingDTO);
    List<CurrencyRequestDTO> getRequests();
}

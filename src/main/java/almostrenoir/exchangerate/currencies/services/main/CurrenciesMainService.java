package almostrenoir.exchangerate.currencies.services.main;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestOutgoingDTO;
import almostrenoir.exchangerate.shared.pagination.PaginatedResult;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CurrenciesMainService {
    Mono<CurrencyFetchOutgoingDTO> getCurrentCurrencyValue(CurrencyFetchIncomingDTO currencyFetchIncomingDTO);
    List<CurrencyRequestOutgoingDTO> getRequests();
    PaginatedResult<CurrencyRequestOutgoingDTO> getRequests(int page);
}

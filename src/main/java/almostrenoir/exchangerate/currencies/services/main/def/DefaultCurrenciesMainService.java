package almostrenoir.exchangerate.currencies.services.main.def;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestOutgoingDTO;
import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.services.currencyfetch.CurrencyFetchService;
import almostrenoir.exchangerate.currencies.services.main.CurrenciesMainService;
import almostrenoir.exchangerate.shared.pagination.PaginatedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCurrenciesMainService implements CurrenciesMainService {

    private final CurrencyFetchService currencyFetchService;
    private final CurrencyRequestRepository currencyRequestRepository;

    @Override
    public Mono<CurrencyFetchOutgoingDTO> getCurrentCurrencyValue(CurrencyFetchIncomingDTO currencyFetchIncomingDTO) {
        return currencyFetchService.getCurrentCurrencyValue(currencyFetchIncomingDTO)
                .map(CurrencyFetchOutgoingDTO::new);
    }

    @Override
    public List<CurrencyRequestOutgoingDTO> getRequests() {
        List<CurrencyRequest> currencyRequests = currencyRequestRepository.findAll();
        return mapCurrencyRequestsToDTOs(currencyRequests);
    }

    @Override
    public PaginatedResult<CurrencyRequestOutgoingDTO> getRequests(int page) {
        PaginatedResult<CurrencyRequest> currencyRequests = currencyRequestRepository.findAll(page);
        return new PaginatedResult<>(
                mapCurrencyRequestsToDTOs(currencyRequests.getContent()),
                currencyRequests.getTotalPages()
        );
    }

    private List<CurrencyRequestOutgoingDTO> mapCurrencyRequestsToDTOs(List<CurrencyRequest> currencyRequests) {
        return currencyRequests.stream()
                .map(CurrencyRequestOutgoingDTO::fromModel)
                .toList();
    }
}

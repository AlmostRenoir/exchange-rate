package almostrenoir.exchangerate.currencies.services.main.def;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestOutgoingDTO;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.services.currencyfetch.CurrencyFetchService;
import almostrenoir.exchangerate.currencies.services.main.CurrenciesMainService;
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
        return currencyRequestRepository.findAll()
                .stream()
                .map(CurrencyRequestOutgoingDTO::fromModel)
                .toList();
    }
}

package almostrenoir.exchangerate.currencies.services.main.def;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestOutgoingDTO;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.services.currencyfetch.CurrencyFetchService;
import almostrenoir.exchangerate.currencies.services.main.CurrenciesMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DefaultCurrenciesMainService implements CurrenciesMainService {

    private final CurrencyFetchService currencyFetchService;
    private final CurrencyRequestRepository currencyRequestRepository;

    @Autowired
    public DefaultCurrenciesMainService(CurrencyFetchService currencyFetchService, CurrencyRequestRepository currencyRequestRepository) {
        this.currencyFetchService = currencyFetchService;
        this.currencyRequestRepository = currencyRequestRepository;
    }

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

package almostrenoir.exchangerate.currencies;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestDTO;
import almostrenoir.exchangerate.currencies.services.main.CurrenciesMainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {

    private final CurrenciesMainService currenciesMainService;

    @Autowired
    public CurrenciesController(CurrenciesMainService currenciesMainService) {
        this.currenciesMainService = currenciesMainService;
    }

    @PostMapping("/get-current-currency-value-command")
    public Mono<CurrencyFetchOutgoingDTO> getCurrentCurrencyValue(
            @RequestBody @Valid CurrencyFetchIncomingDTO currencyFetchIncomingDTO
    ) {
        return currenciesMainService.getCurrentCurrencyValue(currencyFetchIncomingDTO);
    }

    @GetMapping("/requests")
    public List<CurrencyRequestDTO> getRequests() {
        return currenciesMainService.getRequests();
    }

}

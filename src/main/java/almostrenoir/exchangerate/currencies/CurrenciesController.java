package almostrenoir.exchangerate.currencies;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestOutgoingDTO;
import almostrenoir.exchangerate.currencies.services.main.CurrenciesMainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrenciesController {

    private final CurrenciesMainService currenciesMainService;

    @PostMapping("/get-current-currency-value-command")
    public Mono<CurrencyFetchOutgoingDTO> getCurrentCurrencyValue(
            @RequestBody @Valid CurrencyFetchIncomingDTO currencyFetchIncomingDTO
    ) {
        return currenciesMainService.getCurrentCurrencyValue(currencyFetchIncomingDTO);
    }

    @GetMapping("/requests")
    public List<CurrencyRequestOutgoingDTO> getRequests() {
        return currenciesMainService.getRequests();
    }

}

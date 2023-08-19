package almostrenoir.exchangerate.currencies;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestOutgoingDTO;
import almostrenoir.exchangerate.currencies.services.main.CurrenciesMainService;
import almostrenoir.exchangerate.shared.pagination.PaginatedResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
@Validated
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

    @GetMapping("requests/paginated")
    public PaginatedResult<CurrencyRequestOutgoingDTO> getRequests(
            @RequestParam @Min(value = 0, message = "Page cannot be negative number") int page
    ) {
        return currenciesMainService.getRequests(page);
    }

}

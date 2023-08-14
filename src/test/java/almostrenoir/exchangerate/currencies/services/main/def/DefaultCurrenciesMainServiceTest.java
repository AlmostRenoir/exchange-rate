package almostrenoir.exchangerate.currencies.services.main.def;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyFetchOutgoingDTO;
import almostrenoir.exchangerate.currencies.dtos.outgoing.CurrencyRequestOutgoingDTO;
import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.services.currencyfetch.CurrencyFetchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultCurrenciesMainServiceTest {

    @Mock
    private CurrencyFetchService currencyFetchService;

    @Mock
    private CurrencyRequestRepository currencyRequestRepository;

    private DefaultCurrenciesMainService currenciesMainService;

    @BeforeEach
    void setup() {
        currenciesMainService = new DefaultCurrenciesMainService(currencyFetchService, currencyRequestRepository);
    }

    @Test
    void shouldMapCurrentCurrencyValue() {
        BigDecimal currencyValue = new BigDecimal("4.0377");
        when(currencyFetchService.getCurrentCurrencyValue(any())).thenReturn(Mono.just(currencyValue));
        CurrencyFetchIncomingDTO currencyFetchIncomingDTO = mock(CurrencyFetchIncomingDTO.class);

        CurrencyFetchOutgoingDTO result = currenciesMainService.getCurrentCurrencyValue(currencyFetchIncomingDTO).block();

        assertNotNull(result);
        assertEquals(currencyValue, result.getValue());
    }

    @Test
    void shouldMapRequests() {
        List<CurrencyRequest> requests = createCurrencyRequests();
        when(currencyRequestRepository.findAll()).thenReturn(requests);

        List<CurrencyRequestOutgoingDTO> result = currenciesMainService.getRequests();

        assertEquals(requests.size(), result.size());
        CurrencyRequest firstRequest = requests.get(0);
        CurrencyRequestOutgoingDTO firstRequestDTO = result.stream()
                .filter(dto -> dto.getId().equals(firstRequest.getId()))
                .findFirst()
                .orElseThrow();
        assertEquals(firstRequest.getRequester(), firstRequestDTO.getName());
        assertEquals(firstRequest.getCurrency(), firstRequestDTO.getCurrency());
        assertEquals(firstRequest.getRateValue(), firstRequestDTO.getValue());
        assertEquals(firstRequest.getDate(), firstRequestDTO.getDate());
    }

    private List<CurrencyRequest> createCurrencyRequests() {
        CurrencyRequest firstCurrencyRequest = CurrencyRequest.builder()
                .id(UUID.randomUUID())
                .requester("Foo Bar")
                .currency("usd")
                .date(LocalDateTime.now().minusDays(2))
                .rateValue(new BigDecimal("4.0122"))
                .build();

        CurrencyRequest secondCurrencyRequest = CurrencyRequest.builder()
                .id(UUID.randomUUID())
                .requester("John Milton")
                .currency("eur")
                .date(LocalDateTime.now().minusHours(5))
                .rateValue(new BigDecimal("4.4231"))
                .build();

        return List.of(firstCurrencyRequest, secondCurrencyRequest);
    }

    @Test
    void shouldReturnEmptyRequestsListIfNothingFound() {
        when(currencyRequestRepository.findAll()).thenReturn(List.of());

        List<CurrencyRequestOutgoingDTO> result = currenciesMainService.getRequests();

        assertEquals(0, result.size());
    }
}
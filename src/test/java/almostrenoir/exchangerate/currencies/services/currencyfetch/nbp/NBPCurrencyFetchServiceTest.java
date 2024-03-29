package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import almostrenoir.exchangerate.currencies.dtos.incoming.CurrencyFetchIncomingDTO;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.request.repository.NewCurrencyRequest;
import almostrenoir.exchangerate.shared.exceptions.DataNotFoundException;
import almostrenoir.exchangerate.shared.exceptions.ExternalServiceException;
import almostrenoir.exchangerate.shared.httpclient.HttpClient;
import almostrenoir.exchangerate.shared.httpclient.HttpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NBPCurrencyFetchServiceTest {

    private static final CurrencyFetchIncomingDTO INCOMING_DTO = new CurrencyFetchIncomingDTO("usd", "Foo Bar");

    @Mock
    private HttpClient httpClient;

    @Mock
    private CurrencyRequestRepository currencyRequestRepository;

    private NBPCurrencyFetchService currencyFetchService;

    @BeforeEach
    void setup() {
        currencyFetchService = new NBPCurrencyFetchService(httpClient, currencyRequestRepository);
    }

    @Test
    void shouldReturnOnlyValueOnSuccessfulAPICall() {
        NBPTableRecord tableRecord = createNBPTableRecord();
        when(httpClient.get(anyString(), eq(NBPTableRecord.class), anyInt())).thenReturn(Mono.just(tableRecord));
        BigDecimal expectedResult = new BigDecimal("4.0377");
        NewCurrencyRequest expectedCurrencyRequestToPersist = createExpectedCurrencyRequestToPersist(expectedResult);

        BigDecimal result = currencyFetchService.getCurrentCurrencyValue(INCOMING_DTO).block();

        verify(httpClient).get(
                eq("https://api.nbp.pl/api/exchangerates/rates/A/usd/?format=json"),
                eq(NBPTableRecord.class),
                anyInt()
        );
        verify(currencyRequestRepository).add(eq(expectedCurrencyRequestToPersist));
        assertEquals(expectedResult, result);
    }

    private NBPTableRecord createNBPTableRecord() {
        NBPRate rate = new NBPRate(
                "145/A/NBP/2023",
                LocalDate.parse("2023-07-28"),
                new BigDecimal("4.0377"));

        return new NBPTableRecord(
                "A",
                "dolar amerykański",
                "USD",
                List.of(rate));
    }

    private NewCurrencyRequest createExpectedCurrencyRequestToPersist(BigDecimal expectedRateValue) {
        return new NewCurrencyRequest(
                INCOMING_DTO.getName(),
                INCOMING_DTO.getCurrency(),
                expectedRateValue
        );
    }

    @Test
    void shouldThrowExceptionIfDataNotFound() {
        when(httpClient.get(anyString(), eq(NBPTableRecord.class), anyInt()))
                .thenReturn(Mono.error(new HttpException.NotFound()));
        String expectedMessage = "There is no available exchange rate for given currency";

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> currencyFetchService.getCurrentCurrencyValue(INCOMING_DTO).block());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfServiceHadInternalError() {
        when(httpClient.get(anyString(), eq(NBPTableRecord.class), anyInt()))
                .thenReturn(Mono.error(new HttpException.ServerError()));
        String expectedMessage = "NBP service is currently unavailable";

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> currencyFetchService.getCurrentCurrencyValue(INCOMING_DTO).block());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfServiceExceededTimeout() {
        when(httpClient.get(anyString(), eq(NBPTableRecord.class), anyInt()))
                .thenReturn(Mono.error(new HttpException.TimeoutExceeded()));
        String expectedMessage = "NBP service is currently unavailable";

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> currencyFetchService.getCurrentCurrencyValue(INCOMING_DTO).block());
        assertEquals(expectedMessage, exception.getMessage());
    }

}
package almostrenoir.exchangerate.currencies.request.repository.jpa;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JpaCurrencyRequestRepositoryTest {

    @Autowired
    private CurrencyRequestAutoJpaRepository autoJpaRepository;

    private JpaCurrencyRequestRepository currencyRequestRepository;

    @BeforeEach
    void setup() {
        currencyRequestRepository = new JpaCurrencyRequestRepository(autoJpaRepository);
    }

    @Test
    void shouldAddAndFind() {
        CurrencyRequest currencyRequest = createCurrencyRequest();
        LocalDateTime beforeAdd = LocalDateTime.now();

        currencyRequestRepository.add(currencyRequest);
        List<CurrencyRequest> findResult = currencyRequestRepository.findAll();

        assertEquals(1, findResult.size());
        CurrencyRequest currencyRequestFromDB = findResult.get(0);
        assertEquals(currencyRequest.getRequester(), currencyRequestFromDB.getRequester());
        assertEquals(currencyRequest.getCurrency(), currencyRequestFromDB.getCurrency());
        assertEquals(currencyRequest.getRateValue(), currencyRequestFromDB.getRateValue());
        assertNotNull(currencyRequestFromDB.getId());
        assertFalse(currencyRequestFromDB.getDate().isBefore(beforeAdd));
        assertFalse(currencyRequestFromDB.getDate().isAfter(LocalDateTime.now()));
    }

    private CurrencyRequest createCurrencyRequest() {
        return CurrencyRequest.builder()
                .requester("Foo Bar")
                .currency("usd")
                .rateValue(new BigDecimal("4.0377"))
                .build();
    }

}
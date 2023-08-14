package almostrenoir.exchangerate.currencies.request.repository;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewCurrencyRequest {

    private final String requester;
    private final String currency;
    private final BigDecimal rateValue;

}

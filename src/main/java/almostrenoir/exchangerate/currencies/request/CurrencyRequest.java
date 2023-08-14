package almostrenoir.exchangerate.currencies.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CurrencyRequest {

    private final UUID id;
    private final String requester;
    private final String currency;
    private final LocalDateTime date;
    private final BigDecimal rateValue;

}

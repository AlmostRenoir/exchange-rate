package almostrenoir.exchangerate.currencies.dtos.outgoing;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyFetchOutgoingDTO {
    private final BigDecimal value;
}

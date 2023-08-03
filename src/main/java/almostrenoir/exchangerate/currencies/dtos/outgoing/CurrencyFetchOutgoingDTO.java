package almostrenoir.exchangerate.currencies.dtos.outgoing;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CurrencyFetchOutgoingDTO {
    private final BigDecimal value;
}

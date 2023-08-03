package almostrenoir.exchangerate.currencies.dtos.outgoing;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class CurrencyRequestDTO {

    private final UUID id;
    private final String currency;
    private final String name;
    private final LocalDateTime date;
    private final BigDecimal value;

    public static CurrencyRequestDTO fromModel(CurrencyRequest model) {
        return CurrencyRequestDTO.builder()
                .id(model.getId())
                .currency(model.getCurrency())
                .name(model.getRequester())
                .date(model.getDate())
                .value(model.getRateValue())
                .build();
    }
}

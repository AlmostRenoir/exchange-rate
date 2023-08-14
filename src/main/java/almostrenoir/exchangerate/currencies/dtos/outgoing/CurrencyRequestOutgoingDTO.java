package almostrenoir.exchangerate.currencies.dtos.outgoing;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CurrencyRequestOutgoingDTO {

    private final UUID id;
    private final String currency;
    private final String name;
    private final LocalDateTime date;
    private final BigDecimal value;

    public static CurrencyRequestOutgoingDTO fromModel(CurrencyRequest model) {
        return CurrencyRequestOutgoingDTO.builder()
                .id(model.getId())
                .currency(model.getCurrency())
                .name(model.getRequester())
                .date(model.getDate())
                .value(model.getRateValue())
                .build();
    }
}

package almostrenoir.exchangerate.currencies.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder
public class CurrencyRequest {

    private final UUID id;

    @NotBlank
    private final String requester;

    @NotBlank
    private final String currency;

    private final LocalDateTime date;

    @NotNull
    private final BigDecimal rateValue;
}

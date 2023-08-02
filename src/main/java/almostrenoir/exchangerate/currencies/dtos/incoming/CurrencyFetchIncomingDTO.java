package almostrenoir.exchangerate.currencies.dtos.incoming;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class CurrencyFetchIncomingDTO {
    @NotBlank
    private String currency;

    @NotBlank
    private String name;
}

package almostrenoir.exchangerate.currencies.dtos.incoming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class CurrencyFetchIncomingDTO {
    @NotNull
    @Size(min = 3, max = 3)
    private String currency;

    @NotBlank
    private String name;
}

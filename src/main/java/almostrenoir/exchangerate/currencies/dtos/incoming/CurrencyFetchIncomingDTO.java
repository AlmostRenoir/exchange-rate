package almostrenoir.exchangerate.currencies.dtos.incoming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CurrencyFetchIncomingDTO {
    @NotNull(message = "Currency code cannot be null")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters long")
    private final String currency;

    @NotBlank(message = "Name cannot be blank")
    private final String name;
}

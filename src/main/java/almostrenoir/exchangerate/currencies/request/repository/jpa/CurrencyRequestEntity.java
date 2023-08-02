package almostrenoir.exchangerate.currencies.request.repository.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "CurrencyRequest")
@Data
@Builder
public class CurrencyRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final UUID id;

    @NotBlank
    private final String requester;

    @NotBlank
    private final String currency;

    @Builder.Default
    private final LocalDateTime date = LocalDateTime.now();

    @NotNull
    @Column(precision = 8, scale = 4)
    private final BigDecimal rateValue;
}

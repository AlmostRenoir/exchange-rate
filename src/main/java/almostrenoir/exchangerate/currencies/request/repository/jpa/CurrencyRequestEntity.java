package almostrenoir.exchangerate.currencies.request.repository.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "CurrencyRequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String requester;

    @NotNull
    @Size(min = 3, max = 3)
    private String currency;

    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @NotNull
    @Column(precision = 8, scale = 4)
    private BigDecimal rateValue;
}

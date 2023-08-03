package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NBPRate {
    private String no;
    private LocalDate effectiveDate;
    private BigDecimal mid;
}

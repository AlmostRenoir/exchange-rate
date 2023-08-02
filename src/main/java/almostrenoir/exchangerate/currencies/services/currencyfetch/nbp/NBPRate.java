package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@Builder
public class NBPRate {
    private String no;
    private LocalDate effectiveDate;
    private BigDecimal mid;
}

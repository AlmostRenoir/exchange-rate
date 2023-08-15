package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class NBPRate {
    private final String no;
    private final LocalDate effectiveDate;
    private final BigDecimal mid;
}

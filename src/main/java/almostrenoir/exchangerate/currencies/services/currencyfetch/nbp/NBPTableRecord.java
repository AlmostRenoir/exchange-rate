package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NBPTableRecord {
    private String table;
    private String currency;
    private String code;
    private List<NBPRate> rates;

    public List<NBPRate> getRates() {
        return Collections.unmodifiableList(rates);
    }
}

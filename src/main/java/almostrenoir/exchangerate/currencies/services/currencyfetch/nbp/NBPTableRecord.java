package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode
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

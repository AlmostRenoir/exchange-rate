package almostrenoir.exchangerate.currencies.services.currencyfetch.nbp;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
public class NBPTableRecord {
    private final String table;
    private final String currency;
    private final String code;
    private final List<NBPRate> rates;

    public List<NBPRate> getRates() {
        return Collections.unmodifiableList(rates);
    }
}

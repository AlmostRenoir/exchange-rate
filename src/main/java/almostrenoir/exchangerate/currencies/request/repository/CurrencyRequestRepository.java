package almostrenoir.exchangerate.currencies.request.repository;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface CurrencyRequestRepository {
    void add(@Valid CurrencyRequest currencyRequest);
    List<CurrencyRequest> findAll();
}

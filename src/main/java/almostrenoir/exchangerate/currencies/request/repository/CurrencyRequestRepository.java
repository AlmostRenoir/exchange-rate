package almostrenoir.exchangerate.currencies.request.repository;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import almostrenoir.exchangerate.shared.pagination.PaginatedResult;

import java.util.List;

public interface CurrencyRequestRepository {
    void add(NewCurrencyRequest newCurrencyRequest);
    List<CurrencyRequest> findAll();
    PaginatedResult<CurrencyRequest> findAll(int page);
}

package almostrenoir.exchangerate.currencies.request.repository;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;

import java.util.List;

public interface CurrencyRequestRepository {
    void add(NewCurrencyRequest newCurrencyRequest);
    List<CurrencyRequest> findAll();
}

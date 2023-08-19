package almostrenoir.exchangerate.currencies.request.repository.jpa;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.request.repository.NewCurrencyRequest;
import almostrenoir.exchangerate.shared.pagination.PaginatedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaCurrencyRequestRepository implements CurrencyRequestRepository {

    private static final int ITEMS_PER_PAGE = 50;

    private final CurrencyRequestAutoJpaRepository autoJpaRepository;

    @Override
    public void add(NewCurrencyRequest newCurrencyRequest) {
        CurrencyRequestEntity entity = CurrencyRequestEntity.builder()
                .requester(newCurrencyRequest.getRequester())
                .currency(newCurrencyRequest.getCurrency())
                .rateValue(newCurrencyRequest.getRateValue())
                .build();
        autoJpaRepository.save(entity);
    }

    @Override
    public List<CurrencyRequest> findAll() {
        return autoJpaRepository.findAll()
                .stream()
                .map(this::mapEntityToModel)
                .toList();
    }

    @Override
    public PaginatedResult<CurrencyRequest> findAll(int page) {
        Pageable pageable = PageRequest.of(page, ITEMS_PER_PAGE);
        Page<CurrencyRequestEntity> entitiesPage = autoJpaRepository.findAll(pageable);
        List<CurrencyRequest> content = entitiesPage.stream()
                .map(this::mapEntityToModel)
                .toList();
        return new PaginatedResult<>(content, entitiesPage.getTotalPages());
    }

    private CurrencyRequest mapEntityToModel(CurrencyRequestEntity entity) {
        return CurrencyRequest.builder()
                .id(entity.getId())
                .requester(entity.getRequester())
                .currency(entity.getCurrency())
                .date(entity.getDate())
                .rateValue(entity.getRateValue())
                .build();
    }
}

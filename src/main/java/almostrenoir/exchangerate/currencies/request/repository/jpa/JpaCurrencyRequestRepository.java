package almostrenoir.exchangerate.currencies.request.repository.jpa;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaCurrencyRequestRepository implements CurrencyRequestRepository {

    private final JpaRepository<CurrencyRequestEntity, UUID> autoJpaRepository;

    @Autowired
    public JpaCurrencyRequestRepository(JpaRepository<CurrencyRequestEntity, UUID> autoJpaRepository) {
        this.autoJpaRepository = autoJpaRepository;
    }

    @Override
    public void add(@Valid CurrencyRequest currencyRequest) {
        CurrencyRequestEntity entity = CurrencyRequestEntity.builder()
                .requester(currencyRequest.getRequester())
                .currency(currencyRequest.getCurrency())
                .rateValue(currencyRequest.getRateValue())
                .build();
        autoJpaRepository.save(entity);
    }

    @Override
    public List<CurrencyRequest> findAll() {
        return autoJpaRepository.findAll()
                .stream()
                .map(this::mapEntityToModel)
                .collect(Collectors.toUnmodifiableList());
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

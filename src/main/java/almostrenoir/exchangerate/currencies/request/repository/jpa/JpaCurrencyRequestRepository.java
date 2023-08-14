package almostrenoir.exchangerate.currencies.request.repository.jpa;

import almostrenoir.exchangerate.currencies.request.CurrencyRequest;
import almostrenoir.exchangerate.currencies.request.repository.CurrencyRequestRepository;
import almostrenoir.exchangerate.currencies.request.repository.NewCurrencyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaCurrencyRequestRepository implements CurrencyRequestRepository {

    private final JpaRepository<CurrencyRequestEntity, UUID> autoJpaRepository;

    @Autowired
    public JpaCurrencyRequestRepository(JpaRepository<CurrencyRequestEntity, UUID> autoJpaRepository) {
        this.autoJpaRepository = autoJpaRepository;
    }

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

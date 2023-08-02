package almostrenoir.exchangerate.currencies.request.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyRequestAutoJpaRepository extends JpaRepository<CurrencyRequestEntity, UUID> { }

package ml.geekdjenika.apiinfrabaana.repositories;

import ml.geekdjenika.apiinfrabaana.models.Amount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmountRepository extends JpaRepository<Amount, Long> {
    Amount findByValue(long value);
    Amount findByCurrency(String currency);
}

package ml.geekdjenika.apiinfrabaana.repositories;

import ml.geekdjenika.apiinfrabaana.models.Fine;
import ml.geekdjenika.apiinfrabaana.models.Category;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.models.Amount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
    Fine findByCategory(Category category);

    Fine findByCategoryAndAmount(Category category, Amount amount);

    List<Fine> findByInfringements(Infringement infringement);

    boolean existsByCategoryAndAmount(Category category, Amount amount);

}

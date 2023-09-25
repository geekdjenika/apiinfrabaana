package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Fine;
import ml.geekdjenika.apiinfrabaana.Model.Category;
import ml.geekdjenika.apiinfrabaana.Model.Infringement;
import ml.geekdjenika.apiinfrabaana.Model.Amount;
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

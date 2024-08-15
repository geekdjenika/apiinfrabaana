package ml.geekdjenika.apiinfrabaana.repositories;

import ml.geekdjenika.apiinfrabaana.models.Infringement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfringementRepository extends JpaRepository<Infringement, Long> {
    Infringement findByDescription(String description);

    List<Infringement> findByCategoryName(String name);
}

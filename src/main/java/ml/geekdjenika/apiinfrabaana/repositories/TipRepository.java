package ml.geekdjenika.apiinfrabaana.repositories;

import ml.geekdjenika.apiinfrabaana.models.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {

    Tip findByDescription(String description);
}

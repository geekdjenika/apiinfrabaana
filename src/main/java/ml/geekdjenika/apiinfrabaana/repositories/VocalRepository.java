package ml.geekdjenika.apiinfrabaana.repositories;

import ml.geekdjenika.apiinfrabaana.models.Language;
import ml.geekdjenika.apiinfrabaana.models.Vocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocalRepository extends JpaRepository<Vocal, Long> {
    Vocal findByVocal(String vocal);
    List<Vocal> findByLanguage(Language language);
}

package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Langue;
import ml.geekdjenika.apiinfrabaana.Model.Vocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocalRepository extends JpaRepository<Vocal, Long> {
    Vocal findByVocal(String vocal);
    List<Vocal> findByLangue(Langue langue);
}

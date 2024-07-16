package ml.geekdjenika.apiinfrabaana.services.vocal;

import ml.geekdjenika.apiinfrabaana.models.Vocal;

import java.util.List;
import java.util.Optional;

public interface VocalService {
    Vocal addVocal(Vocal vocal);
    List<Vocal> listAll();
    Vocal listOne(long id);
    Optional<Vocal> update(Vocal vocal, long id);
    void deleteVocal(long id);

}

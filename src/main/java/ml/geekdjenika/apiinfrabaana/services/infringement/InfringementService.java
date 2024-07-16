package ml.geekdjenika.apiinfrabaana.services.infringement;

import ml.geekdjenika.apiinfrabaana.models.Infringement;

import java.util.List;
import java.util.Optional;

public interface InfringementService {

    Infringement addInfringement(Infringement infringement);
    Infringement getInfringement(long id);
    List<Infringement> getAll();
    List<Infringement> getAllByCategory(String categorie);
    Optional<Infringement> update(Infringement infringement, long id);
    void delete(long id);

    Infringement superAdd(Infringement infringement);

    Infringement findById(long id);
}

package ml.geekdjenika.apiinfrabaana.services.fine;

import ml.geekdjenika.apiinfrabaana.models.Fine;

import java.util.List;
import java.util.Optional;

public interface FineService {

    Fine addFine(Fine fine);
    Fine getFine(long id);
    List<Fine> getAllFine();
    Optional<Fine> updateFine(Fine fine, long id);
    void deleteFine(long id);

}

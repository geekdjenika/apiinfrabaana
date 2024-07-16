package ml.geekdjenika.apiinfrabaana.Service.fine;

import ml.geekdjenika.apiinfrabaana.Model.Fine;

import java.util.List;
import java.util.Optional;

public interface FineService {

    Fine addFine(Fine fine);
    Fine getFine(long id);
    List<Fine> getAllFine();
    Optional<Fine> updateFine(Fine fine, long id);
    void deleteFine(long id);

}

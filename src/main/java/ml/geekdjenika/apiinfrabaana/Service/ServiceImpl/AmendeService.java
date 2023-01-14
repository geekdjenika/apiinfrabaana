package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Amende;

import java.util.List;
import java.util.Optional;

public interface AmendeService {

    Amende addFine(Amende amende);
    Amende getFine(long id);
    List<Amende> getAllFine();
    Optional<Amende> updateFine(Amende amende, long id);
    void deleteFine(long id);

}

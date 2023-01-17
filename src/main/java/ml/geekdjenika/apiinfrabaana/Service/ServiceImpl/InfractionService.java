package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Amende;
import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface InfractionService {

    Infraction addInfraction(Infraction infraction);
    Infraction getInfraction(long id);
    List<Infraction> getAll();
    List<Infraction> getAllByUser(Utilisateur utilisateur);
    Optional<Infraction> update(Infraction infraction, long id);
    void delete(long id);

}

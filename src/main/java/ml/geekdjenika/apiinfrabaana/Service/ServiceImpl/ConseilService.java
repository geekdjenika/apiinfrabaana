package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Conseil;
import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface ConseilService {

    Conseil addConseil(Conseil conseil);
    Conseil getConseil(long id);
    List<Conseil> getAll();
    List<Conseil> getAllByInfraction(Infraction infraction);
    Optional<Conseil> update(Conseil conseil, long id);
    void delete(long id);

}

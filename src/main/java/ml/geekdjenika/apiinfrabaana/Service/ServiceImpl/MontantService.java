package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Montant;

import java.util.List;
import java.util.Optional;

public interface MontantService {
    Montant addMontant(Montant montant);
    Montant getMontant(long id);
    List<Montant> getAllMontant();
    Optional<Montant> updateMontant(Montant montant, long id);
    void deleteMontant(long id);
}

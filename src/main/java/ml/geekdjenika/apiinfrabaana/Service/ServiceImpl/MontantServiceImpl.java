package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Montant;
import ml.geekdjenika.apiinfrabaana.Repository.MontantRepository;
import ml.geekdjenika.apiinfrabaana.Repository.ReponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MontantServiceImpl implements MontantService{

    @Autowired
    MontantRepository montantRepository;

    @Override
    public Montant addMontant(Montant montant) {
        return montantRepository.save(montant);
    }

    @Override
    public Montant getMontant(long id) {
        return montantRepository.findById(id).get();
    }

    @Override
    public List<Montant> getAllMontant() {
        return montantRepository.findAll();
    }

    @Override
    public Optional<Montant> updateMontant(Montant montant, long id) {
        return montantRepository.findById(id).map(
                montant1 -> {
                    montant1.setMontant(montant.getMontant());
                    montant1.setDevise(montant.getDevise());
                    return montantRepository.save(montant1);
                }
        );
    }

    @Override
    public void deleteMontant(long id) {
        montantRepository.deleteById(id);

    }
}

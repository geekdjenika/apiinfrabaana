package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import ml.geekdjenika.apiinfrabaana.Repository.InfraRepository;
import ml.geekdjenika.apiinfrabaana.Repository.InfractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class InfractionServiceImpl implements InfractionService{

    @Autowired
    private InfraRepository infraRepository;

    @Autowired
    InfractionRepository infractionRepository;

    @Override
    public Infraction addInfraction(Infraction infraction) {
        return infractionRepository.save(infraction);
    }

    @Override
    public Infraction getInfraction(long id) {
        return infractionRepository.findById(id).get();
    }

    @Override
    public List<Infraction> getAll() {
        return infractionRepository.findAll();
    }

    @Override
    public List<Infraction> getAllByCategorie(String categorie) {
        return infraRepository.findInfractionsByCategory(categorie);
    }

    @Override
    public Optional<Infraction> update(Infraction infraction, long id) {
        return infractionRepository.findById(id).map(
                infractionamodifier -> {
                    infractionamodifier.setDescription(infraction.getDescription());
                    infractionamodifier.setReference(infraction.getReference());
                    infractionamodifier.setVocals(infraction.getVocals());
                    //infractionamodifier.setUtilisateur(infraction.getUtilisateur());
                    return infractionRepository.save(infractionamodifier);
                }
        );
    }

    @Override
    public void delete(long id) {
        infractionRepository.deleteById(id);
    }

    @Override
    public Infraction superAdd(Infraction infraction) {
        return infractionRepository.save(infraction);
    }
}

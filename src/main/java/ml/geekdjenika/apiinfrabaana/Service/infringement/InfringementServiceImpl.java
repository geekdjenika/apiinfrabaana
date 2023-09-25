package ml.geekdjenika.apiinfrabaana.Service.infringement;

import ml.geekdjenika.apiinfrabaana.Model.Infringement;
import ml.geekdjenika.apiinfrabaana.Repository.InfraRepository;
import ml.geekdjenika.apiinfrabaana.Repository.InfringementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class InfringementServiceImpl implements InfringementService {

    @Autowired
    private InfraRepository infraRepository;

    @Autowired
    InfringementRepository infringementRepository;

    @Override
    public Infringement addInfringement(Infringement infringement) {
        return infringementRepository.save(infringement);
    }

    @Override
    public Infringement getInfringement(long id) {
        return infringementRepository.findById(id).get();
    }

    @Override
    public List<Infringement> getAll() {
        return infringementRepository.findAll();
    }

    @Override
    public List<Infringement> getAllByCategory(String categorie) {
        return infraRepository.findInfringementsByCategory(categorie);
    }

    @Override
    public Optional<Infringement> update(Infringement infringement, long id) {
        return infringementRepository.findById(id).map(
                infractionamodifier -> {
                    infractionamodifier.setDescription(infringement.getDescription());
                    infractionamodifier.setReference(infringement.getReference());
                    infractionamodifier.setVocals(infringement.getVocals());
                    //infractionamodifier.setUtilisateur(infraction.getUtilisateur());
                    return infringementRepository.save(infractionamodifier);
                }
        );
    }

    @Override
    public void delete(long id) {
        infringementRepository.deleteById(id);
    }

    @Override
    public Infringement superAdd(Infringement infringement) {
        return infringementRepository.save(infringement);
    }
}

package ml.geekdjenika.apiinfrabaana.services.infringement;

import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.repositories.InfraRepository;
import ml.geekdjenika.apiinfrabaana.repositories.InfringementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class InfringementServiceImpl implements InfringementService {

    @Autowired
    private InfraRepository infraRepository;

    @Autowired
    InfringementRepository repository;

    @Override
    public Infringement addInfringement(Infringement infringement) {
        return repository.save(infringement);
    }

    @Override
    public Infringement getInfringement(long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Infringement> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Infringement> getAllByCategory(String category) {
        return infraRepository.findInfringementsByCategory(category);
    }

    @Override
    public Optional<Infringement> update(Infringement infringement, long id) {

        return repository.findById(id).map(
                infringementToUpdate -> {
                    infringementToUpdate.setDescription(infringement.getDescription());
                    infringementToUpdate.setReference(infringement.getReference());
                    infringementToUpdate.setVocals(infringement.getVocals());
                    return repository.save(infringementToUpdate);
                }
        );
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public Infringement superAdd(Infringement infringement) {
        return repository.save(infringement);
    }

    @Override
    public Infringement findById(long id) {
        Infringement infringement = repository.findById(id).orElse(null);
        if (infringement == null) throw new NotFoundException("Infraction introuvable !");
        return infringement;
    }
}

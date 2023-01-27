package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Conseil;
import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import ml.geekdjenika.apiinfrabaana.Repository.ConseilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConseilServiceImpl implements ConseilService{

    @Autowired
    ConseilRepository conseilRepository;

    @Override
    public Conseil addConseil(Conseil conseil) {
        return conseilRepository.save(conseil);
    }

    @Override
    public Conseil getConseil(long id) {
        return conseilRepository.findById(id).get();
    }

    @Override
    public List<Conseil> getAll() {
        return conseilRepository.findAll();
    }

    /*@Override
    public List<Conseil> getAllByInfraction(Infraction infraction) {
        return conseilRepository.findByInfraction(infraction);
    }*/

    @Override
    public Optional<Conseil> update(Conseil conseil, long id) {
        return conseilRepository.findById(id).map(
                conseilamodifier -> {
                    conseilamodifier.setConseil(conseil.getConseil());
                    if (!conseil.getInfractions().isEmpty()) conseilamodifier.setInfractions(conseil.getInfractions());
                    return conseilRepository.save(conseilamodifier);
                }
        );
    }

    @Override
    public void delete(long id) {
        conseilRepository.deleteById(id);
    }
}

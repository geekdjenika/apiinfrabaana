package ml.geekdjenika.apiinfrabaana.Service.tip;

import ml.geekdjenika.apiinfrabaana.Model.Tip;
import ml.geekdjenika.apiinfrabaana.Repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipServiceImpl implements TipService {

    @Autowired
    TipRepository tipRepository;

    @Override
    public Tip addTip(Tip tip) {
        return tipRepository.save(tip);
    }

    @Override
    public Tip getTip(long id) {
        return tipRepository.findById(id).get();
    }

    @Override
    public List<Tip> getAll() {
        return tipRepository.findAll();
    }

    /*@Override
    public List<Conseil> getAllByInfraction(Infraction infraction) {
        return conseilRepository.findByInfraction(infraction);
    }*/

    @Override
    public Optional<Tip> update(Tip tip, long id) {
        return tipRepository.findById(id).map(
                conseilamodifier -> {
                    conseilamodifier.setTip(tip.getTip());
                    if (!tip.getInfringements().isEmpty()) conseilamodifier.setInfringements(tip.getInfringements());
                    conseilamodifier.setVocals(tip.getVocals());
                    return tipRepository.save(conseilamodifier);
                }
        );
    }

    @Override
    public void delete(long id) {
        tipRepository.deleteById(id);
    }
}

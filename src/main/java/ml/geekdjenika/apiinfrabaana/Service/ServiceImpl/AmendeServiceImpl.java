package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Amende;
import ml.geekdjenika.apiinfrabaana.Repository.AmendeRepository;
import ml.geekdjenika.apiinfrabaana.Repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AmendeServiceImpl implements AmendeService {

    @Autowired
    AmendeRepository amendeRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Override
    public Amende addFine(Amende amende) {
        return amendeRepository.save(amende);
    }

    @Override
    public Amende getFine(long id) {
        return amendeRepository.findById(id).get();
    }

    @Override
    public List<Amende> getAllFine() {
        return amendeRepository.findAll();
    }

    @Override
    public Optional<Amende> updateFine(Amende amende, long id) {
        return amendeRepository.findById(id).map(
                amende1 -> {
                    amende1.setCategorie(amende.getCategorie());
                    amende1.setMontant(amende.getMontant());
                    amende1.setVocals(amende.getVocals());
                    return amendeRepository.save(amende1);
                }
        );
    }

    @Override
    public void deleteFine(long id) {
        amendeRepository.deleteById(id);
    }
}

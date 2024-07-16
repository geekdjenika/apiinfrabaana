package ml.geekdjenika.apiinfrabaana.Service.vocal;

import ml.geekdjenika.apiinfrabaana.Model.Vocal;
import ml.geekdjenika.apiinfrabaana.Repository.VocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VocalServiceImpl implements VocalService{

    @Autowired
    VocalRepository vocalRepository;

    @Override
    public Vocal addVocal(Vocal vocal) {
        return vocalRepository.save(vocal);
    }

    @Override
    public List<Vocal> listAll() {
        return vocalRepository.findAll();
    }

    @Override
    public Vocal listOne(long id) {
        return vocalRepository.findById(id).get();
    }

    @Override
    public Optional<Vocal> update(Vocal vocal, long id) {
        return vocalRepository.findById(id).map(
                vocal1 -> {
                    vocal1.setVocal(vocal.getVocal());
                    if (vocal.getLanguage() != null) vocal1.setLanguage(vocal.getLanguage());
                    return vocalRepository.save(vocal1);
                }
        );
    }

    @Override
    public void deleteVocal(long id) {
        vocalRepository.deleteById(id);
    }
}

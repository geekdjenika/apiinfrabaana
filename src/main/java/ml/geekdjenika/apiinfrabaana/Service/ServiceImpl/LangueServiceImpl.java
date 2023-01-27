package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Langue;
import ml.geekdjenika.apiinfrabaana.Repository.LangueRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LangueServiceImpl implements LangueService{

    @Autowired
    LangueRepository langueRepository;

    @Override
    public Langue addLangue(Langue langue) {
        return langueRepository.save(langue);
    }

    @Override
    public Langue findLangueByLabel(String label) {
        return langueRepository.findByLabel(label);
    }

    @Override
    public List<Langue> findAllLangue() {
        return langueRepository.findAll();
    }

    @Override
    public Langue findOneLangue(long id) {
        return langueRepository.findById(id).get();
    }
}

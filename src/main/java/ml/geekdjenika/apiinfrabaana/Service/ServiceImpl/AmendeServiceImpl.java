package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Amende;
import ml.geekdjenika.apiinfrabaana.Repository.AmendeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AmendeServiceImpl implements AmendeService {

    @Autowired
    AmendeRepository amendeRepository;

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
                    amende1.setType(amende.getType());
                    amende1.setMontant(amende.getMontant());
                    return amendeRepository.save(amende1);
                }
        );
    }

    @Override
    public void deleteFine(long id) {

    }
}

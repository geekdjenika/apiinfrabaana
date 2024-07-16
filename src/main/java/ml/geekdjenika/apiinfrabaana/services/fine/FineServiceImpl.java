package ml.geekdjenika.apiinfrabaana.services.fine;

import ml.geekdjenika.apiinfrabaana.models.Fine;
import ml.geekdjenika.apiinfrabaana.repositories.FineRepository;
import ml.geekdjenika.apiinfrabaana.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FineServiceImpl implements FineService {

    @Autowired
    FineRepository fineRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Fine addFine(Fine fine) {
        return fineRepository.save(fine);
    }

    @Override
    public Fine getFine(long id) {
        return fineRepository.findById(id).get();
    }

    @Override
    public List<Fine> getAllFine() {
        return fineRepository.findAll();
    }

    @Override
    public Optional<Fine> updateFine(Fine fine, long id) {
        return fineRepository.findById(id).map(
                amende1 -> {
                    amende1.setCategory(fine.getCategory());
                    amende1.setAmount(fine.getAmount());
                    amende1.setVocals(fine.getVocals());
                    return fineRepository.save(amende1);
                }
        );
    }

    @Override
    public void deleteFine(long id) {
        fineRepository.deleteById(id);
    }
}

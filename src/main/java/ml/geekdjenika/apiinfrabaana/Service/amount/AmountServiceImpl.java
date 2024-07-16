package ml.geekdjenika.apiinfrabaana.Service.amount;

import ml.geekdjenika.apiinfrabaana.Model.Amount;
import ml.geekdjenika.apiinfrabaana.Repository.AmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AmountServiceImpl implements AmountService {

    @Autowired
    AmountRepository amountRepository;

    @Override
    public Amount addAmount(Amount amount) {
        return amountRepository.save(amount);
    }

    @Override
    public Amount getAmount(long id) {
        return amountRepository.findById(id).get();
    }

    @Override
    public List<Amount> getAllAmount() {
        return amountRepository.findAll();
    }

    @Override
    public Optional<Amount> updateAmount(Amount amount, long id) {
        return amountRepository.findById(id).map(
                montant1 -> {
                    montant1.setAmount(amount.getAmount());
                    montant1.setCurrency(amount.getCurrency());
                    return amountRepository.save(montant1);
                }
        );
    }

    @Override
    public void deleteMontant(long id) {
        amountRepository.deleteById(id);

    }
}

package ml.geekdjenika.apiinfrabaana.services.amount;

import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Amount;
import ml.geekdjenika.apiinfrabaana.repositories.AmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AmountServiceImpl implements AmountService {

    @Autowired
    AmountRepository repository;

    @Override
    public AmountResponse save(Amount amount) {
        return mapToResponse(repository.save(amount));
    }

    @Override
    public AmountResponse findById(long id) {
        Amount amount = repository.findById(id).orElse(null);
        if (amount == null) throw new NotFoundException("Ce montant est introuvable !");
        return mapToResponse(amount);
    }

    @Override
    public List<AmountResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public AmountResponse update(Amount amount) {
        Amount amountToUpdate = repository.findById(amount.getId()).orElse(null);
        if (amountToUpdate == null) throw new NotFoundException("Montant introuvable !");
        amountToUpdate.setAmount(amount.getAmount());
        amountToUpdate.setCurrency(amount.getCurrency());
        return mapToResponse(amountToUpdate);
    }

    @Override
    public void delete(long id) {
        Amount amountToDelete = repository.findById(id).orElse(null);
        if (amountToDelete == null) throw new NotFoundException("Montant introuvable !");
        repository.delete(amountToDelete);
    }

    @Override
    public AmountResponse mapToResponse(Amount amount) {
        return AmountResponse.builder()
                .id(amount.getId())
                .amount(amount.getAmount())
                .currency(amount.getCurrency())
                .build();
    }

    @Override
    public List<AmountResponse> mapToResponse(List<Amount> amounts) {
        List<AmountResponse> amountResponses = new ArrayList<>();
        amounts.forEach(amount -> amountResponses.add(mapToResponse(amount)));
        return amountResponses;
    }
}

package ml.geekdjenika.apiinfrabaana.Service.amount;

import ml.geekdjenika.apiinfrabaana.Model.Amount;

import java.util.List;
import java.util.Optional;

public interface AmountService {
    Amount addAmount(Amount amount);
    Amount getAmount(long id);
    List<Amount> getAllAmount();
    Optional<Amount> updateAmount(Amount amount, long id);
    void deleteMontant(long id);
}

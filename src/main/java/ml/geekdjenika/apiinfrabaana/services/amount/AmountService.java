package ml.geekdjenika.apiinfrabaana.services.amount;

import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.models.Amount;

import java.util.List;

public interface AmountService {
    AmountResponse save(Amount amount);
    AmountResponse findById(long id);
    List<AmountResponse> findAll();
    AmountResponse update(Amount amount);
    void delete(long id);
    AmountResponse mapToResponse(Amount amount);
    List<AmountResponse> mapToResponse(List<Amount> amounts);
}

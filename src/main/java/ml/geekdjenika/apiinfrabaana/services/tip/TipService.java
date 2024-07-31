package ml.geekdjenika.apiinfrabaana.services.tip;

import ml.geekdjenika.apiinfrabaana.dto.tip.TipResponse;
import ml.geekdjenika.apiinfrabaana.models.Tip;

import java.util.List;

public interface TipService {
    TipResponse save(Tip tip);
    TipResponse findById(long id);
    List<TipResponse> findAll();
    TipResponse update(Tip tip);
    void delete(long id);
    TipResponse mapToResponse(Tip tip);
    List<TipResponse> mapToResponse(List<Tip> tips);
}

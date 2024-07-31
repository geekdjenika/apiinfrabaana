package ml.geekdjenika.apiinfrabaana.services.vocal;

import ml.geekdjenika.apiinfrabaana.dto.vocal.VocalResponse;
import ml.geekdjenika.apiinfrabaana.models.Vocal;

import java.util.List;

public interface VocalService {
    VocalResponse save(Vocal vocal);
    List<VocalResponse> findAll();
    VocalResponse findById(long id);
    VocalResponse update(Vocal vocal);
    void delete(long id);
    VocalResponse mapToResponse(Vocal vocal);
    List<VocalResponse> mapToResponse(List<Vocal> vocals);
}

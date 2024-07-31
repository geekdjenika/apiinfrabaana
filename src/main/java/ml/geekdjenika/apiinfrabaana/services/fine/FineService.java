package ml.geekdjenika.apiinfrabaana.services.fine;

import ml.geekdjenika.apiinfrabaana.dto.fine.FineResponse;
import ml.geekdjenika.apiinfrabaana.models.Fine;

import java.util.List;

public interface FineService {
    FineResponse save(Fine fine);
    FineResponse update(Fine fine);
    FineResponse findById(long id);
    List<FineResponse> findAll();
    void delete(long id);
    FineResponse mapToResponse(Fine fine);
    List<FineResponse> mapToResponse(List<Fine> fines);
}

package ml.geekdjenika.apiinfrabaana.services.infringement;

import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.models.Infringement;

import java.util.List;

public interface InfringementService {

    InfringementResponse save(Infringement infringement);
    InfringementResponse update(Infringement infringement);
    InfringementResponse findById(long id);
    List<InfringementResponse> findAll();
    List<InfringementResponse> findByCategoryName(String name);

    void setFines(Infringement infringement, Infringement infringementToUpdate);

    void setVocals(Infringement infringement, Infringement infringementToUpdate);

    void delete(long id);
    InfringementResponse mapToResponse(Infringement infringement);
    List<InfringementResponse> mapToResponse(List<Infringement> infringements);

}

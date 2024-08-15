package ml.geekdjenika.apiinfrabaana.services.infringement;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.repositories.InfringementRepository;
import ml.geekdjenika.apiinfrabaana.services.vocal.VocalService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InfringementServiceImpl implements InfringementService {

    private final InfringementRepository repository;
    private final VocalService vocalService;

    @Override
    public InfringementResponse save(Infringement infringement) {
        return mapToResponse(repository.save(infringement));
    }

    @Override
    public InfringementResponse findById(long id) {
        Infringement infringement = repository.findById(id).orElse(null);
        if (infringement == null) throw new NotFoundException("Infraction introuvable !");
        return mapToResponse(infringement);
    }

    @Override
    public List<InfringementResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public List<InfringementResponse> findByCategoryName(String name) {
        return mapToResponse(repository.findByCategoryName(name));
    }

    @Override
    public InfringementResponse update(Infringement infringement) {
        Infringement infringementToUpdate = repository.findById(infringement.getId()).orElse(null);
        if (infringementToUpdate == null) throw new NotFoundException("Infraction introuvable !");

        infringementToUpdate.setDescription(infringement.getDescription());
        infringementToUpdate.setReference(infringement.getReference());
        if (infringement.getVocals() != null) {
            infringementToUpdate.getVocals().clear();
            infringement.getVocals().forEach(vocal -> infringementToUpdate.getVocals().add(vocal));
        }

        return mapToResponse(infringementToUpdate);
    }

    @Override
    public void delete(long id) {
        Infringement infringement = repository.findById(id).orElse(null);
        if (infringement == null) throw new NotFoundException("Infraction introuvable !");
        repository.delete(infringement);
    }

    @Override
    public InfringementResponse mapToResponse(Infringement infringement) {
        return InfringementResponse.builder()
                .id(infringement.getId())
                .reference(infringement.getReference())
                .description(infringement.getDescription())
                .category(CategoryResponse.builder()
                        .id(infringement.getCategory().getId())
                        .name(infringement.getCategory().getName())
                        .build())
                .vocals(vocalService.mapToResponse(infringement.getVocals()))
                .build();
    }

    @Override
    public List<InfringementResponse> mapToResponse(List<Infringement> infringements) {
        infringements.sort(Comparator.comparing(Infringement::getId).reversed());
        List<InfringementResponse> infringementResponses = new ArrayList<>();
        infringements.forEach(infringement -> infringementResponses.add(mapToResponse(infringement)));
        return infringementResponses;
    }

}

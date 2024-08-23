package ml.geekdjenika.apiinfrabaana.services.infringement;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.dto.fine.FineResponse;
import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Fine;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.repositories.FineRepository;
import ml.geekdjenika.apiinfrabaana.repositories.InfringementRepository;
import ml.geekdjenika.apiinfrabaana.repositories.VocalRepository;
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
    private final FineRepository fineRepository;
    private final VocalRepository vocalRepository;

    @Override
    public InfringementResponse save(Infringement infringement) {
        Infringement savedInfringement = repository.save(infringement);
        setVocals(infringement, savedInfringement);
        setFines(infringement, savedInfringement);
        return mapToResponse(savedInfringement);
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
        infringementToUpdate.setCategory(infringement.getCategory());
        setVocals(infringement, infringementToUpdate);
        setFines(infringement, infringementToUpdate);
        return mapToResponse(infringementToUpdate);
    }

    @Override
    public void setFines(Infringement infringement, Infringement infringementToUpdate) {
        if (infringement.getFines() != null) {
            infringementToUpdate.getFines().clear();
            infringement.getFines().forEach(fine -> infringementToUpdate.getFines().add(fine));
        }
    }

    @Override
    public void setVocals(Infringement infringement, Infringement infringementToUpdate) {
        if (infringement.getVocals() != null) {
            infringementToUpdate.getVocals().clear();
            infringement.getVocals().forEach(vocal -> {
                vocal.setInfringement(infringementToUpdate);
                vocalRepository.save(vocal);
            });
        }
    }

    @Override
    public void delete(long id) {
        Infringement infringement = repository.findById(id).orElse(null);
        if (infringement == null) throw new NotFoundException("Infraction introuvable !");
        repository.delete(infringement);
    }

    @Override
    public InfringementResponse mapToResponse(Infringement infringement) {
        List<Fine> fines = infringement.getFines();
        List<FineResponse> fineResponses = new ArrayList<>();
        fines.forEach(fine -> fineResponses.add(FineResponse.builder()
                .id(fine.getId())
                .amount(AmountResponse.builder()
                        .id(fine.getAmount().getId())
                        .value(fine.getAmount().getValue())
                        .currency(fine.getAmount().getCurrency())
                        .build())
                .category(CategoryResponse.builder()
                        .id(fine.getCategory().getId())
                        .name(fine.getCategory().getName())
                        .build())
                .build()));
        return InfringementResponse.builder()
                .id(infringement.getId())
                .reference(infringement.getReference())
                .description(infringement.getDescription())
                .fines(fineResponses)
                .category(infringement.getCategory() == null ? null : CategoryResponse.builder()
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

package ml.geekdjenika.apiinfrabaana.services.fine;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.dto.fine.FineResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Fine;
import ml.geekdjenika.apiinfrabaana.repositories.FineRepository;
import ml.geekdjenika.apiinfrabaana.services.infringement.InfringementService;
import ml.geekdjenika.apiinfrabaana.services.vocal.VocalService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository repository;
    private final VocalService vocalService;
    private final InfringementService infringementService;

    @Override
    public FineResponse save(Fine fine) {
        return mapToResponse(repository.save(fine));
    }

    @Override
    public FineResponse findById(long id) {
        Fine fine = repository.findById(id).orElse(null);
        if (fine == null) throw new NotFoundException("Infraction introuvable !");
        return mapToResponse(fine);
    }

    @Override
    public List<FineResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public FineResponse update(Fine fine) {
        Fine fineToUpdate = repository.findById(fine.getId()).orElse(null);
        if (fineToUpdate == null) throw new NotFoundException("Infraction introuvable !");
        fineToUpdate.setCategory(fine.getCategory());
        fineToUpdate.setAmount(fine.getAmount());
        fineToUpdate.setVocals(fine.getVocals());
        return mapToResponse(fineToUpdate);
    }

    @Override
    public void delete(long id) {
        Fine fineToDelete = repository.findById(id).orElse(null);
        if (fineToDelete == null) throw new NotFoundException("Infraction introuvable !");
        repository.delete(fineToDelete);
    }

    @Override
    public FineResponse mapToResponse(Fine fine) {
        return FineResponse.builder()
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
                .vocals(vocalService.mapToResponse(fine.getVocals()))
                .infringements(infringementService.mapToResponse(fine.getInfringements()))
                .build();
    }

    @Override
    public List<FineResponse> mapToResponse(List<Fine> fines) {
        List<FineResponse> fineResponses = new ArrayList<>();
        if (fines != null) {
            for (Fine fine : fines) {
                fineResponses.add(mapToResponse(fine));
            }
        }
        return fineResponses;
    }
}

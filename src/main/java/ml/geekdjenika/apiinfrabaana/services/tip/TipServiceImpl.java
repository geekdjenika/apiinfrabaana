package ml.geekdjenika.apiinfrabaana.services.tip;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.dto.tip.TipResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.models.Tip;
import ml.geekdjenika.apiinfrabaana.repositories.TipRepository;
import ml.geekdjenika.apiinfrabaana.services.vocal.VocalService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipRepository repository;
    private final VocalService vocalService;

    @Override
    public TipResponse save(Tip tip) {
        return mapToResponse(repository.save(tip));
    }

    @Override
    public TipResponse findById(long id) {
        Tip tip = repository.findById(id).orElse(null);
        if (tip == null) throw new NotFoundException("Conseil introuvable !");
        return mapToResponse(tip);
    }

    @Override
    public List<TipResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public TipResponse update(Tip tip) {
        Tip tipToUpdate = repository.findById(tip.getId()).orElse(null);
        if (tipToUpdate == null) throw new NotFoundException("Conseil introuvable !");
        tipToUpdate.setDescription(tip.getDescription());
        if (tip.getVocals() != null) {
            tip.getVocals().forEach(vocal -> tipToUpdate.getVocals().add(vocal));
        }
        if (tip.getInfringements() != null) {
            tip.getInfringements().forEach(infringement -> tipToUpdate.getInfringements().add(infringement));
        }
        return mapToResponse(tipToUpdate);
    }

    @Override
    public void delete(long id) {
        Tip tip = repository.findById(id).orElse(null);
        if (tip == null) throw new NotFoundException("Conseil introuvable !");
        repository.delete(tip);
    }

    @Override
    public TipResponse mapToResponse(Tip tip) {
        List<Infringement> infringements = tip.getInfringements();
        List<InfringementResponse> infringementResponses = new ArrayList<>();
        infringements.forEach(infringement -> infringementResponses.add(InfringementResponse.builder()
                .id(infringement.getId())
                .description(infringement.getDescription())
                .reference(infringement.getReference())
                .build()));
        return TipResponse.builder()
                .id(tip.getId())
                .description(tip.getDescription())
                .vocals(vocalService.mapToResponse(tip.getVocals()))
                .infringements(infringementResponses)
                .build();
    }

    @Override
    public List<TipResponse> mapToResponse(List<Tip> tips) {
        tips.sort(Comparator.comparing(Tip::getId).reversed());
        List<TipResponse> tipResponses = new ArrayList<>();
        tips.forEach(tip -> tipResponses.add(mapToResponse(tip)));
        return tipResponses;
    }
}

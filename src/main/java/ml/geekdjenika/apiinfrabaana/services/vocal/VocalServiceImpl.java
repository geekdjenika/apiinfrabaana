package ml.geekdjenika.apiinfrabaana.services.vocal;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.vocal.VocalResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Vocal;
import ml.geekdjenika.apiinfrabaana.repositories.VocalRepository;
import ml.geekdjenika.apiinfrabaana.services.language.LanguageService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VocalServiceImpl implements VocalService{

    private final VocalRepository repository;
    private final LanguageService languageService;

    @Override
    public VocalResponse save(Vocal vocal) {
        return mapToResponse(repository.save(vocal));
    }

    @Override
    public List<VocalResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public VocalResponse findById(long id) {
        Vocal vocal = repository.findById(id).orElse(null);
        if (vocal == null) throw new NotFoundException("Audio introuvable !");
        return mapToResponse(vocal);
    }

    @Override
    public VocalResponse update(Vocal vocal) {
        Vocal vocalToUpdate = repository.findById(vocal.getId()).orElse(null);
        if (vocalToUpdate == null) throw new NotFoundException("Audio introuvable !");
        vocalToUpdate.setName(vocal.getName());
        vocalToUpdate.setLanguage(vocal.getLanguage());
        vocalToUpdate.setTip(vocal.getTip());
        vocalToUpdate.setFine(vocal.getFine());
        vocalToUpdate.setInfringement(vocal.getInfringement());
        return mapToResponse(vocal);
    }

    @Override
    public void delete(long id) {
        Vocal vocal = repository.findById(id).orElse(null);
        if (vocal == null) throw new NotFoundException("Audio introuvable !");
        repository.delete(vocal);
    }

    @Override
    public VocalResponse mapToResponse(Vocal vocal) {
        return VocalResponse.builder()
                .id(vocal.getId())
                .name(vocal.getName())
                .language(languageService.mapToResponse(vocal.getLanguage()))
                .build();
    }

    @Override
    public List<VocalResponse> mapToResponse(List<Vocal> vocals) {
        vocals.sort(Comparator.comparing(Vocal::getId).reversed());
        List<VocalResponse> vocalResponses = new ArrayList<>();
        vocals.forEach(vocal -> vocalResponses.add(mapToResponse(vocal)));
        return vocalResponses;
    }
}

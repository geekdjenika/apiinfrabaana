package ml.geekdjenika.apiinfrabaana.services.language;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.language.LanguageResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Language;
import ml.geekdjenika.apiinfrabaana.repositories.LanguageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository repository;

    @Override
    public LanguageResponse save(Language language) {
        Language languageToSave = repository.findByLabel(language.getLabel());
        if (languageToSave != null) throw new NotFoundException("Cette langue existe déjà !");
        return mapToResponse(repository.save(language));
    }

    @Override
    public LanguageResponse update(Language language) {
        Language languageToUpdate = repository.findById(language.getId()).orElse(null);
        if (languageToUpdate == null) throw new NotFoundException("Langue introuvable !");
        languageToUpdate.setLabel(language.getLabel());
        return mapToResponse(languageToUpdate);
    }

    @Override
    public LanguageResponse findByLabel(String label) {
        return mapToResponse(repository.findByLabel(label));
    }

    @Override
    public List<LanguageResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public LanguageResponse findById(long id) {
        Language language = repository.findById(id).orElse(null);
        if (language == null) throw new NotFoundException("Langue introuvable !");
        return mapToResponse(language);
    }

    @Override
    public void delete(long id) {
        Language language = repository.findById(id).orElse(null);
        if (language == null) throw new NotFoundException("Langue introuvable !");
        repository.delete(language);
    }

    @Override
    public LanguageResponse mapToResponse(Language language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .label(language.getLabel())
                .build();
    }

    @Override
    public List<LanguageResponse> mapToResponse(List<Language> languages) {
        languages.sort(Comparator.comparing(Language::getId).reversed());
        List<LanguageResponse> languageResponses = new ArrayList<>();
        languages.forEach(language -> languageResponses.add(mapToResponse(language)));
        return languageResponses;
    }
}

package ml.geekdjenika.apiinfrabaana.services.language;

import ml.geekdjenika.apiinfrabaana.dto.language.LanguageResponse;
import ml.geekdjenika.apiinfrabaana.models.Language;

import java.util.List;

public interface LanguageService {
    LanguageResponse save(Language language);
    LanguageResponse update(Language language);
    LanguageResponse findByLabel(String label);
    List<LanguageResponse> findAll();
    LanguageResponse findById(long id);
    LanguageResponse mapToResponse(Language language);
    List<LanguageResponse> mapToResponse(List<Language> languages);
}

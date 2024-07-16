package ml.geekdjenika.apiinfrabaana.services.language;

import ml.geekdjenika.apiinfrabaana.models.Language;

import java.util.List;

public interface LanguageService {
    Language addLanguage(Language language);
    Language findLanguageByLabel(String label);
    List<Language> findAllLanguage();
    Language findOneLanguage(long id);
}

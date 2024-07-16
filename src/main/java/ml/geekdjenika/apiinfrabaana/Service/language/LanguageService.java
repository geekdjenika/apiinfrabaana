package ml.geekdjenika.apiinfrabaana.Service.language;

import ml.geekdjenika.apiinfrabaana.Model.Language;

import java.util.List;

public interface LanguageService {
    Language addLanguage(Language language);
    Language findLanguageByLabel(String label);
    List<Language> findAllLanguage();
    Language findOneLanguage(long id);
}

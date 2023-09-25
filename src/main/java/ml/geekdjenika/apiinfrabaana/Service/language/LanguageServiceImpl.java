package ml.geekdjenika.apiinfrabaana.Service.language;

import ml.geekdjenika.apiinfrabaana.Model.Language;
import ml.geekdjenika.apiinfrabaana.Repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public Language addLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Language findLanguageByLabel(String label) {
        return languageRepository.findByLabel(label);
    }

    @Override
    public List<Language> findAllLanguage() {
        return languageRepository.findAll();
    }

    @Override
    public Language findOneLanguage(long id) {
        return languageRepository.findById(id).get();
    }
}

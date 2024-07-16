package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.Language;
import ml.geekdjenika.apiinfrabaana.Repository.LanguageRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/new")
@CrossOrigin(origins = "*", maxAge = 3600)
@ToString
public class NewController {
    private final LanguageRepository languageRepository;

    public NewController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @GetMapping("/langue/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Language> getAll() {
        return languageRepository.findAll();
    }



}

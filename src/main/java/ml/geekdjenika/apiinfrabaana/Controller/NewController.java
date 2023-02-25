package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.Langue;
import ml.geekdjenika.apiinfrabaana.Model.Montant;
import ml.geekdjenika.apiinfrabaana.Repository.LangueRepository;
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
    private final LangueRepository langueRepository;

    public NewController(LangueRepository langueRepository) {
        this.langueRepository = langueRepository;
    }

    @GetMapping("/langue/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Langue> getAll() {
        return langueRepository.findAll();
    }



}

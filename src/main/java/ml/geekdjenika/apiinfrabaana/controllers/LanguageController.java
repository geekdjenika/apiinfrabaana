package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.language.LanguageResponse;
import ml.geekdjenika.apiinfrabaana.models.Language;
import ml.geekdjenika.apiinfrabaana.services.language.LanguageService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/language")
@CrossOrigin
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public LanguageResponse save(@RequestBody Language language) {
        return service.save(language);
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public LanguageResponse update(@RequestBody Language Language) {
        return service.update(Language);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public LanguageResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/label/{label}")
    @PostAuthorize("hasAuthority('USER')")
    public LanguageResponse findByLabel(@PathVariable String label) {
        return service.findByLabel(label);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<LanguageResponse> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }



}

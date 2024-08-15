package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.vocal.VocalResponse;
import ml.geekdjenika.apiinfrabaana.models.Vocal;
import ml.geekdjenika.apiinfrabaana.services.vocal.VocalService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/vocal")
@RequiredArgsConstructor
public class VocalController {

    private final VocalService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public VocalResponse save(@RequestBody Vocal vocal) {
        return service.save(vocal);
    }
}

package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.fine.FineResponse;
import ml.geekdjenika.apiinfrabaana.models.Fine;
import ml.geekdjenika.apiinfrabaana.services.fine.FineService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fine")
@CrossOrigin
@RequiredArgsConstructor
public class FineController {

    private final FineService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public FineResponse save(@RequestBody Fine fine) {
        return service.save(fine);
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public FineResponse update(@RequestBody Fine fine) {
        return service.update(fine);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public FineResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<FineResponse> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}

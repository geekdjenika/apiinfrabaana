package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.services.infringement.InfringementService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/infringement")
@CrossOrigin
@RequiredArgsConstructor
public class InfringementController {

    private final InfringementService service;

    @PostMapping("/add/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public InfringementResponse save(@RequestBody Infringement infringement) {
        return service.save(infringement);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public InfringementResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<InfringementResponse> findAll() {
        return service.findAll();
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public InfringementResponse update(Infringement infringement) {
        return service.update(infringement);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/category-name/{categoryName}")
    @PostAuthorize("hasAuthority('USER')")
    public List<InfringementResponse> findByCategoryName(@PathVariable String categoryName) {
        return service.findByCategoryName(categoryName);
    }
}

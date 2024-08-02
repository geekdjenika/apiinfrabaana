package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.tip.TipResponse;
import ml.geekdjenika.apiinfrabaana.models.Tip;
import ml.geekdjenika.apiinfrabaana.services.tip.TipService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tip")
@CrossOrigin
@RequiredArgsConstructor
public class TipController {

    private final TipService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public TipResponse save(@RequestBody Tip tip) {
        return service.save(tip);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public TipResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<TipResponse> findAll() {
        return service.findAll();
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public TipResponse update(@RequestBody Tip tip) {
        return service.update(tip);
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}

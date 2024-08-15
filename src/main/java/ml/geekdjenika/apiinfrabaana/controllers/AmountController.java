package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.models.Amount;
import ml.geekdjenika.apiinfrabaana.services.amount.AmountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amount")
@CrossOrigin
@RequiredArgsConstructor
public class AmountController {
    private final AmountService service;

    @PostMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AmountResponse save(@RequestBody Amount amount) {
        return service.save(amount);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public AmountResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<AmountResponse> findAll() {
        return service.findAll();
    }

    @PutMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AmountResponse update(@RequestBody Amount amount) {
        return service.update(amount);
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}

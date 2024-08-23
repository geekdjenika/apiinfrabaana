package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.models.Category;
import ml.geekdjenika.apiinfrabaana.services.category.CategoryService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse save(@RequestBody Category category) {
        return service.save(category);
    }

    @PutMapping("")
    @PostAuthorize("hasAuthority('ADMIN')")
    public CategoryResponse update(@RequestBody Category category) {
        return service.update(category);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<CategoryResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public CategoryResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}

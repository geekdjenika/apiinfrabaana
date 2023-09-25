package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.Category;
import ml.geekdjenika.apiinfrabaana.Service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorie")
@CrossOrigin(origins = "*", maxAge = 3600)
@ToString
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Category> getAllCategorie() {
        return categoryService.getAll();
    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Category getCategory(@PathVariable long id) {
        return categoryService.getCategory(id);
    }

}

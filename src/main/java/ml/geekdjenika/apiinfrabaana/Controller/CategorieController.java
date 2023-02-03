package ml.geekdjenika.apiinfrabaana.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.Categorie;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorie")
@CrossOrigin(origins = "*", maxAge = 3600)
@ToString
@Api(value = "Endpoints pour les infractions")
public class CategorieController {

    @Autowired
    CategorieService categorieService;

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    @ApiOperation(value = "Get all toutes les catégories")
    public List<Categorie> getAllCategorie() {
        return categorieService.getAll();
    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    @ApiOperation(value = "Get une catégorie")
    public Categorie getCategorie(@PathVariable long id) {
        return categorieService.getCategorie(id);
    }

}

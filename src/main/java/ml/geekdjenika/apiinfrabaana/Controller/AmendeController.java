package ml.geekdjenika.apiinfrabaana.Controller;

import ml.geekdjenika.apiinfrabaana.Model.Montant;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.MontantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/amende")
public class AmendeController {

    @Autowired
    MontantService montantService;

    @PostMapping("/montant/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Montant addMontant(@RequestBody Montant montant) {
        return montantService.addMontant(montant);
    }

    @GetMapping("/montant/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Montant getMontant(@PathVariable long id) {
        return montantService.getMontant(id);
    }

    @GetMapping("/montant/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Montant> getAll() {
        return montantService.getAllMontant();
    }

    @PutMapping("/montant/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Montant> update(@RequestBody Montant montant, @PathVariable long id) {
        return montantService.updateMontant(montant,id);
    }

    @DeleteMapping("/montant/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable long id) {
        montantService.deleteMontant(id);
        return "Montant supprimé avec succès !";
    }

}

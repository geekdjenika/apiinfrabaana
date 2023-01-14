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
    public String addMontant(@RequestBody Montant montant) {
        Montant montant1 = montantService.addMontant(montant);
        return montant1.getMontant() + " " + montant1.getDevise();
    }

    @GetMapping("/montant/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public String getMontant(@PathVariable long id) {
        Montant montant1 = montantService.getMontant(id);
        return montant1.getMontant() + " " + montant1.getDevise();
    }

    @GetMapping("/montant/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Montant> getAll() {
        return montantService.getAllMontant();
    }

    @PutMapping("/montant/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String update(@RequestBody Montant montant, @PathVariable long id) {
        Montant montant1 = montantService.updateMontant(montant,id).get();
        return montant1.getMontant() + " " + montant1.getDevise();
    }

    @DeleteMapping("/montant/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable long id) {
        montantService.deleteMontant(id);
        return "Montant supprimé avec succès !";
    }

}

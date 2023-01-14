package ml.geekdjenika.apiinfrabaana.Controller;

import ml.geekdjenika.apiinfrabaana.Model.Amende;
import ml.geekdjenika.apiinfrabaana.Model.Montant;
import ml.geekdjenika.apiinfrabaana.Repository.MontantRepository;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.AmendeService;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.MontantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/amende")
public class AmendeController {

    @Autowired
    MontantService montantService;
    @Autowired
    private AmendeService amendeService;
    @Autowired
    private MontantRepository montantRepository;

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

    //###########################AMENDE#################################
    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Amende addFine(
            @Param("type") String type,
            @Param("montant") long montant) {
        Amende amende = new Amende();
        amende.setType(type);
        amende.setMontant(montantRepository.findByMontant(montant));
        return amendeService.addFine(amende);
    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Amende getFine(@PathVariable long id) {
        return amendeService.getFine(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Amende> getAllFine() {
        return amendeService.getAllFine();
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Amende> updateFine(
            @Param("type") String type,
            @Param("montant") long montant,
            @PathVariable long id) {
        Amende amende = new Amende();
        amende.setType(type);
        amende.setMontant(montantRepository.findByMontant(montant));
        return amendeService.updateFine(amende,id);
    }

    @DeleteMapping("/montant/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteFine(@PathVariable long id) {
        amendeService.deleteFine(id);
        return "Amende supprimé avec succès !";
    }

}

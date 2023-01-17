package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import ml.geekdjenika.apiinfrabaana.Repository.AmendeRepository;
import ml.geekdjenika.apiinfrabaana.Repository.MontantRepository;
import ml.geekdjenika.apiinfrabaana.Repository.UtilisateurRepository;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.InfractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/infraction")
@ToString
public class InfractionController {

    @Autowired
    InfractionService infractionService;
    @Autowired
    private AmendeRepository amendeRepository;
    @Autowired
    private MontantRepository montantRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/add/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Infraction addInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("amende1") long amende1,
            @Param("amende2") long amende2,
            @PathVariable long id) {
        Infraction infraction = new Infraction();
        infraction.setDescription(description);
        infraction.setReference(reference);
        infraction.getAmendes().add(amendeRepository.findByMontant(montantRepository.findByMontant(amende1)));
        infraction.getAmendes().add(amendeRepository.findByMontant(montantRepository.findByMontant(amende2)));
        infraction.setUtilisateur(utilisateurRepository.findById(id).get());

        return infractionService.addInfraction(infraction);

    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Infraction getInfraction(@PathVariable long id) {
        return infractionService.getInfraction(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infraction> getAllInfractions() {
        return infractionService.getAll();
    }

    @GetMapping("/get/allbyamende")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infraction> getAllInfractionsByUser(@PathVariable long id) {
        return infractionService.getAllByUser(utilisateurRepository.findById(id).get());
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Infraction> updateInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("amende1") Long amende1,
            @Param("amende2") Long amende2,
            @PathVariable long id) {
        Infraction infraction = new Infraction();
        infraction.setDescription(description);
        infraction.setReference(reference);
        if (amende1 != null) infraction.getAmendes().add(amendeRepository.findByMontant(montantRepository.findByMontant(amende1)));
        if (amende2 != null) infraction.getAmendes().add(amendeRepository.findByMontant(montantRepository.findByMontant(amende2)));
        return infractionService.update(infraction,id);
    }

}

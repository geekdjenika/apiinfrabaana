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
            @Param("amende") long amende,
            @PathVariable long id) {
        Infraction infraction = new Infraction();
        infraction.setDescription(description);
        infraction.setReference(reference);
        infraction.setAmende(amendeRepository.findByMontant(montantRepository.findByMontant(amende)));
        infraction.setUtilisateur(utilisateurRepository.findById(id).get());

        return infractionService.addInfraction(infraction);

    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Infraction getInfraction(@PathVariable long id) {
        return infractionService.getInfraction(id);
    }

    @GetMapping("/montant/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infraction> getAllInfractions() {
        
    }

}

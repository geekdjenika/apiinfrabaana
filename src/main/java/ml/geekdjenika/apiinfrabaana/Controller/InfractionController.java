package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.*;
import ml.geekdjenika.apiinfrabaana.Repository.*;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.InfractionService;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.VocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/infraction")
@CrossOrigin(origins = "*", maxAge = 3600)
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
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private VocalService vocalService;
    @Autowired
    private ConseilRepository conseilRepository;
    @Autowired
    private InfractionRepository infractionRepository;
    @Autowired
    private LangueRepository langueRepository;
    VocalController vocalController = new VocalController(vocalService, conseilRepository, infractionRepository, amendeRepository, montantRepository, langueRepository);

    @PostMapping("/add/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Infraction addInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("file") MultipartFile file,
            @Param("langue") String langue,
            @Param("amende1") String amende1,
            @Param("amende2") String amende2,
            @PathVariable long id) throws IOException {
        Infraction infraction = new Infraction();
        infraction.setDescription(description);
        infraction.setReference(reference);
        infraction.setAmendes(new ArrayList<>());

        //Amende 1
        if (amende1 != null) {
            if (montantRepository.findByMontant(Long.parseLong(amende1)) != null)
                if (amendeRepository.findByMontant(montantRepository.findByMontant(Long.parseLong(amende1))) != null) infraction.getAmendes().add(amendeRepository.findByMontant(montantRepository.findByMontant(Long.parseLong(amende1))));
        }

        //Amende 2
        if (amende2 != null) {
            if (montantRepository.findByMontant(Long.parseLong(amende2)) != null)
                if (amendeRepository.findByMontant(montantRepository.findByMontant(Long.parseLong(amende2))) != null) infraction.getAmendes().add(amendeRepository.findByMontant(montantRepository.findByMontant(Long.parseLong(amende2))));
        }


        infraction.setUtilisateur(utilisateurRepository.findById(id).get());
        infraction = infractionService.addInfraction(infraction);

        if (file != null) {
            //Vocal
            String uploadDir = System.getProperty("user.dir") + "/assets/aud";
            //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            Audio.saveAudio(uploadDir, convFile);
            Vocal vocal = new Vocal();
            if (langueRepository.findByLabel(langue) != null) vocal.setLangue(langueRepository.findByLabel(langue));
            vocal.setInfraction(infraction);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }

        return infraction;

    }

    @PostMapping("/addsuper/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Infraction superAddInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("categorieamende1") String categorieamende1,
            @Param("devise1") String devise1,
            @Param("montant1") String montant1,
            @Param("categorieamende2") String categorieamende2,
            @Param("devise2") String devise2,
            @Param("montant2") String montant2,
            @Param("file") MultipartFile file,
            @Param("langue") String langue,
            @PathVariable long id
    ) throws IOException {
        Categorie categorie1 = categorieRepository.findByCategorie(categorieamende1);
        Amende amende1 = new Amende();
        Montant nouveaumontant1;
        if (((montant1) != null) || (devise1 != null)) {
            nouveaumontant1 = new Montant(devise1,Long.parseLong(montant1));
            if (montantRepository.findByMontant(Long.parseLong(montant1)) == null) {
                nouveaumontant1 = montantRepository.save(nouveaumontant1);
            } else {
                nouveaumontant1 = montantRepository.findByMontant(Long.parseLong(montant1));
            }
            amende1.setMontant(nouveaumontant1);
        }

        amende1.setCategorie(categorie1);
        amende1 = amendeRepository.save(amende1);

        Categorie categorie2 = categorieRepository.findByCategorie(categorieamende2);
        Amende amende2 = new Amende();
        Montant nouveaumontant2;
        if (((montant2) != null) || (devise2 != null)) {
            nouveaumontant2 = new Montant(devise2,Long.parseLong(montant2));
            if (montantRepository.findByMontant(Long.parseLong(montant2)) == null) {
                nouveaumontant2 = montantRepository.save(nouveaumontant2);
            } else {
                nouveaumontant2 = montantRepository.findByMontant(Long.parseLong(montant2));
            }
            amende2.setMontant(nouveaumontant2);
        }



        amende2.setCategorie(categorie2);
        amende2 = amendeRepository.save(amende2);

        Infraction infraction = new Infraction();
        infraction.setAmendes(new ArrayList<>());
        infraction.setUtilisateur(utilisateurRepository.findById(id).get());
        infraction.setDescription(description);
        infraction.setReference(reference);
        infraction.getAmendes().add(amende1);
        infraction.getAmendes().add(amende2);

        infraction = infractionService.superAdd(infraction);

        if (file != null) {
            //Vocal
            String uploadDir = System.getProperty("user.dir") + "/assets/aud";
            //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            Audio.saveAudio(uploadDir, convFile);
            Vocal vocal = new Vocal();
            if (langueRepository.findByLabel(langue) != null) vocal.setLangue(langueRepository.findByLabel(langue));
            vocal.setInfraction(infraction);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }

        return infraction;
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

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteInfraction(@PathVariable long id) {
        infractionService.delete(id);
        return "Infraction supprimée avec succès !";
    }

    @GetMapping("/get/allbycategorie")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infraction> getInfractionsByCategory(@Param("categorie") String categorie) {
        return infractionService.getAllByCategorie(categorie);
    }

}

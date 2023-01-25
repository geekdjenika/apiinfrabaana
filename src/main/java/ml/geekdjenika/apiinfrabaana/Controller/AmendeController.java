package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.Amende;
import ml.geekdjenika.apiinfrabaana.Model.Montant;
import ml.geekdjenika.apiinfrabaana.Repository.CategorieRepository;
import ml.geekdjenika.apiinfrabaana.Repository.MontantRepository;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.AmendeService;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.MontantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/amende")
@ToString
public class AmendeController {

    @Autowired
    MontantService montantService;
    @Autowired
    private AmendeService amendeService;
    @Autowired
    private MontantRepository montantRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @PostMapping("/montant/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Montant addMontant(@RequestBody Montant montant) {
        Montant montant1 = montantService.addMontant(montant);
        return montant1;
    }

    @GetMapping("/montant/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Montant getMontant(@PathVariable long id) {
        Montant montant1 = montantService.getMontant(id);
        return montant1;
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
            @Param("montant") long montant,
            @Param("file")MultipartFile file) throws IOException {
        Amende amende = new Amende();
        if (categorieRepository.findByCategorie(type)!=null) amende.setCategorie(categorieRepository.findByCategorie(type));
        String uploadDir = System.getProperty("user.dir") + "/assets/aud";
        //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        Audio.saveAudio(uploadDir, convFile);
        amende.setAudio(file.getOriginalFilename());
        if (montantRepository.findByMontant(montant) != null) amende.setMontant(montantRepository.findByMontant(montant));
        else {
            Montant montant1 = montantService.addMontant(new Montant("FCFA",montant));
            amende.setMontant(montant1);
        }
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
        amende.setCategorie(categorieRepository.findByCategorie(type));;
        amende.setMontant(montantRepository.findByMontant(montant));
        return amendeService.updateFine(amende,id);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteFine(@PathVariable long id) {
        amendeService.deleteFine(id);
        return "Amende supprimée avec succès !";
    }

}

package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Configuration.Excel;
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
import java.util.*;

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
            @Param("langue") String langue) throws IOException {
        Infraction infraction = new Infraction();
        infraction.setDescription(description);
        infraction.setReference(reference);


        //infraction.setUtilisateur(utilisateurRepository.findById(id).get());
        infraction = infractionService.addInfraction(infraction);

        if (file != null) {
            //Vocal
            String uploadDir = Audio.SOURCE_DIR+"aud";//System.getProperty("user.dir") + "/assets/aud";
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

    @PostMapping("/addsuper")
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
            @Param("langue") String langue
    ) throws IOException {

        Infraction infraction = new Infraction();
        infraction.setDescription(description);
        infraction.setReference(reference);

        infractionService.superAdd(infraction);
        infraction = infractionRepository.findByDescription(description);

        Categorie categorie1 = categorieRepository.findByCategorie(categorieamende1);
        Amende amende1 = new Amende();
        //Montant 1
        Montant nouveaumontant1;
        if (((montant1) != null) || (devise1 != null)) {
            nouveaumontant1 = new Montant(devise1,Long.parseLong(montant1));
            if (montantRepository.findByMontant(Long.parseLong(montant1)) == null) {
                montantRepository.save(nouveaumontant1);
                nouveaumontant1 = montantRepository.findByMontant(Long.parseLong(montant1));
                //Amende 1
                amende1.setMontant(nouveaumontant1);
                amende1.setCategorie(categorie1);
                amende1.getInfractions().add(infraction);
                amendeRepository.save(amende1);
            } else {
                nouveaumontant1 = montantRepository.findByMontant(Long.parseLong(montant1));
                //Amende 1
                if (amendeRepository.existsByCategorieAndMontant(categorie1,nouveaumontant1)) {
                    amende1 = amendeRepository.findByCategorieAndMontant(categorie1,nouveaumontant1);
                    amende1.getInfractions().add(infraction);
                    amendeRepository.save(amende1);
                }
                else {
                    amende1.setMontant(nouveaumontant1);
                    amende1.setCategorie(categorie1);
                    amende1.getInfractions().add(infraction);
                    amendeRepository.save(amende1);
                }
            }

        }


        Categorie categorie2 = categorieRepository.findByCategorie(categorieamende2);
        Amende amende2 = new Amende();
        //Montant 2
        Montant nouveaumontant2;
        if (((montant2) != null) || (devise2 != null)) {
            nouveaumontant2 = new Montant(devise2,Long.parseLong(montant2));
            if (montantRepository.findByMontant(Long.parseLong(montant2)) == null) {
                montantRepository.save(nouveaumontant2);
                nouveaumontant2 = montantRepository.findByMontant(Long.parseLong(montant2));
                //Amende 2
                amende2.setMontant(nouveaumontant2);
                amende2.setCategorie(categorie2);
                amende2.getInfractions().add(infraction);
                amendeRepository.save(amende2);
            } else {
                nouveaumontant2 = montantRepository.findByMontant(Long.parseLong(montant2));
                //Amende 2
                if (amendeRepository.existsByCategorieAndMontant(categorie2,nouveaumontant2)) {
                    amende2 = amendeRepository.findByCategorieAndMontant(categorie2,nouveaumontant2);
                    amende2.getInfractions().add(infraction);
                    amendeRepository.save(amende2);
                }
                else {
                    amende2.setMontant(nouveaumontant2);
                    amende2.setCategorie(categorie2);
                    amende2.getInfractions().add(infraction);
                    amendeRepository.save(amende2);
                }
            }

        }

        if (file != null) {
            //Vocal
            String uploadDir = Audio.SOURCE_DIR+"aud";//System.getProperty("user.dir") + "/assets/aud";
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
        List<Infraction> listainversee = infractionService.getAll();
        Collections.reverse(listainversee);
        return listainversee;
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Infraction> updateInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("file")MultipartFile file,
            @Param("langue") String langue,
            @PathVariable long id) throws IOException {
        Infraction infraction = infractionRepository.findById(id).get();
        if (description !=null) infraction.setDescription(description);
        if (reference !=null) infraction.setReference(reference);

        if (file != null) {
            //Vocal
            String uploadDir = Audio.SOURCE_DIR+"aud";//System.getProperty("user.dir") + "/assets/aud";
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
        return infractionService.update(infraction,id);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteInfraction(@PathVariable long id) {

        Infraction infraction = infractionRepository.findById(id).get();

        List<Amende> amendesassociees = amendeRepository.findByInfractions(infraction);
        for (Amende amende :
                amendesassociees) {
            amende.getInfractions().remove(infraction);
            amendeRepository.save(amende);
        }

        infractionService.delete(id);
        return "Infraction supprimée avec succès !";
    }

    @GetMapping("/get/allbycategorie")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infraction> getInfractionsByCategory(@Param("categorie") String categorie) {
        return infractionService.getAllByCategorie(categorie);
    }

    @PostMapping("/importer")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<ExcelDto> importer(@Param("excel") MultipartFile excel) throws IOException {
        List<ExcelDto> excelDtos = Excel.importer(excel);
        for (ExcelDto exceldata :
                excelDtos) {
            superAddInfraction(
                    exceldata.getDescription(),
                    exceldata.getReference(),
                    exceldata.getCategorie1(),
                    exceldata.getDevise1(),
                    exceldata.getMontant1(),
                    exceldata.getCategorie2(),
                    exceldata.getDevise2(),
                    exceldata.getMontant2(),
                    null,
                    null);
        }
        return excelDtos;
    }

}

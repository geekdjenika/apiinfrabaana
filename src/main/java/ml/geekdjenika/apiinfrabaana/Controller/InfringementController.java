package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Configuration.Excel;
import ml.geekdjenika.apiinfrabaana.Model.*;
import ml.geekdjenika.apiinfrabaana.Repository.*;
import ml.geekdjenika.apiinfrabaana.Service.infringement.InfringementService;
import ml.geekdjenika.apiinfrabaana.Service.vocal.VocalService;
import ml.geekdjenika.apiinfrabaana.dto.ExcelDto;
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
public class InfringementController {

    @Autowired
    InfringementService infringementService;
    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private AmountRepository amountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VocalService vocalService;
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private InfringementRepository infringementRepository;
    @Autowired
    private LanguageRepository languageRepository;
    VocalController vocalController = new VocalController(vocalService, tipRepository, infringementRepository, fineRepository, amountRepository, languageRepository);

    @PostMapping("/add/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Infringement addInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("file") MultipartFile file,
            @Param("langue") String langue) throws IOException {
        Infringement infringement = new Infringement();
        infringement.setDescription(description);
        infringement.setReference(reference);


        //infraction.setUtilisateur(utilisateurRepository.findById(id).get());
        infringement = infringementService.addInfringement(infringement);

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
            if (languageRepository.findByLabel(langue) != null) vocal.setLanguage(languageRepository.findByLabel(langue));
            vocal.setInfringement(infringement);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }

        return infringement;

    }

    @PostMapping("/addsuper")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Infringement superAddInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("finecategory1") String finecategory1,
            @Param("currency1") String currency1,
            @Param("amount1") String amount1,
            @Param("finecategory2") String finecategory2,
            @Param("currency2") String currency2,
            @Param("amount2") String amount2,
            @Param("file") MultipartFile file,
            @Param("language") String language
    ) throws IOException {

        Infringement infringement = new Infringement();
        infringement.setDescription(description);
        infringement.setReference(reference);

        infringementService.superAdd(infringement);
        infringement = infringementRepository.findByDescription(description);

        Category category1 = categoryRepository.findByCategory(finecategory1);
        Fine fine1 = new Fine();
        //First amount
        Amount newamount1;
        if (((amount1) != null) || (currency1 != null)) {
            newamount1 = new Amount(currency1,Long.parseLong(amount1));
            if (amountRepository.findByAmount(Long.parseLong(amount1)) == null) {
                amountRepository.save(newamount1);
                newamount1 = amountRepository.findByAmount(Long.parseLong(amount1));
                //First fine
                fine1.setAmount(newamount1);
                fine1.setCategory(category1);
                fine1.getInfringements().add(infringement);
                fineRepository.save(fine1);
            } else {
                newamount1 = amountRepository.findByAmount(Long.parseLong(amount1));
                //Amende 1
                if (fineRepository.existsByCategoryAndAmount(category1,newamount1)) {
                    fine1 = fineRepository.findByCategoryAndAmount(category1,newamount1);
                    fine1.getInfringements().add(infringement);
                    fineRepository.save(fine1);
                }
                else {
                    fine1.setAmount(newamount1);
                    fine1.setCategory(category1);
                    fine1.getInfringements().add(infringement);
                    fineRepository.save(fine1);
                }
            }

        }


        Category category2 = categoryRepository.findByCategory(finecategory2);
        Fine fine2 = new Fine();
        //2nd amound
        Amount newamount2;
        if (((amount2) != null) || (currency2 != null)) {
            newamount2 = new Amount(currency2,Long.parseLong(amount2));
            if (amountRepository.findByAmount(Long.parseLong(amount2)) == null) {
                amountRepository.save(newamount2);
                newamount2 = amountRepository.findByAmount(Long.parseLong(amount2));
                //2nd fine
                fine2.setAmount(newamount2);
                fine2.setCategory(category2);
                fine2.getInfringements().add(infringement);
                fineRepository.save(fine2);
            } else {
                newamount2 = amountRepository.findByAmount(Long.parseLong(amount2));
                //2nd fine
                if (fineRepository.existsByCategoryAndAmount(category2,newamount2)) {
                    fine2 = fineRepository.findByCategoryAndAmount(category2,newamount2);
                    fine2.getInfringements().add(infringement);
                    fineRepository.save(fine2);
                }
                else {
                    fine2.setAmount(newamount2);
                    fine2.setCategory(category2);
                    fine2.getInfringements().add(infringement);
                    fineRepository.save(fine2);
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
            if (languageRepository.findByLabel(language) != null) vocal.setLanguage(languageRepository.findByLabel(language));
            vocal.setInfringement(infringement);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }

        return infringement;
    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Infringement getInfraction(@PathVariable long id) {
        return infringementService.getInfringement(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infringement> getAllInfractions() {
        List<Infringement> listToInverse = infringementService.getAll();
        Collections.reverse(listToInverse);
        return listToInverse;
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Infringement> updateInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("file")MultipartFile file,
            @Param("langue") String langue,
            @PathVariable long id) throws IOException {
        Infringement infringement = infringementRepository.findById(id).get();
        if (description !=null) infringement.setDescription(description);
        if (reference !=null) infringement.setReference(reference);

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
            if (languageRepository.findByLabel(langue) != null) vocal.setLanguage(languageRepository.findByLabel(langue));
            vocal.setInfringement(infringement);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }
        return infringementService.update(infringement,id);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteInfraction(@PathVariable long id) {

        Infringement infringement = infringementRepository.findById(id).get();

        List<Fine> associatedFine = fineRepository.findByInfringements(infringement);
        for (Fine fine :
                associatedFine) {
            fine.getInfringements().remove(infringement);
            fineRepository.save(fine);
        }

        infringementService.delete(id);
        return "Infraction supprimée avec succès !";
    }

    @GetMapping("/get/allbycategorie")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infringement> getInfractionsByCategory(@Param("categorie") String categorie) {
        return infringementService.getAllByCategory(categorie);
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

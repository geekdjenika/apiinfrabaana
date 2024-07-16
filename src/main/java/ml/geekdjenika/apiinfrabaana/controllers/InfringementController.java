package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.configs.Audio;
import ml.geekdjenika.apiinfrabaana.configs.ExcelConfig;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.*;
import ml.geekdjenika.apiinfrabaana.repositories.*;
import ml.geekdjenika.apiinfrabaana.services.infringement.InfringementService;
import ml.geekdjenika.apiinfrabaana.services.vocal.VocalService;
import ml.geekdjenika.apiinfrabaana.dto.Excel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
@CrossOrigin
@ToString
public class InfringementController {

    @Autowired
    InfringementService service;
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
    public Infringement addInfringement(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("file") MultipartFile file,
            @Param("language") String language) throws IOException {
        Infringement infringement = new Infringement();
        infringement.setDescription(description);
        infringement.setReference(reference);


        //infraction.setUtilisateur(utilisateurRepository.findById(id).get());
        infringement = service.addInfringement(infringement);

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

    @PostMapping("/add-super")
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

        service.superAdd(infringement);
        infringement = infringementRepository.findByDescription(description);

        this.buildInfringement(finecategory1, currency1, amount1, infringement);


        this.buildInfringement(finecategory2, currency2, amount2, infringement);

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

    private void buildInfringement(String category, String currency, String amount, Infringement infringement) {
        Category category1 = categoryRepository.findByCategory(category);
        Fine fine1 = new Fine();
        //First amount
        Amount newamount1;
        if ((amount != null) || (currency != null)) {
            newamount1 = new Amount(currency,Long.parseLong(amount));
            if (amountRepository.findByAmount(Long.parseLong(amount)) == null) {
                amountRepository.save(newamount1);
                newamount1 = amountRepository.findByAmount(Long.parseLong(amount));
                //First fine
                fine1.setAmount(newamount1);
                fine1.setCategory(category1);
                fine1.getInfringements().add(infringement);
                fineRepository.save(fine1);
            } else {
                newamount1 = amountRepository.findByAmount(Long.parseLong(amount));
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
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Infringement findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infringement> getAll() {
        List<Infringement> listToInverse = service.getAll();
        Collections.reverse(listToInverse);
        return listToInverse;
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Infringement> updateInfraction(
            @Param("description") String description,
            @Param("reference") String reference,
            @Param("file")MultipartFile file,
            @Param("language") String language,
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
            if (languageRepository.findByLabel(language) != null) vocal.setLanguage(languageRepository.findByLabel(language));
            vocal.setInfringement(infringement);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }
        return service.update(infringement,id);
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

        service.delete(id);
        return "Infraction supprimée avec succès !";
    }

    @GetMapping("/get/allbycategorie")
    @PostAuthorize("hasAuthority('USER')")
    public List<Infringement> getInfractionsByCategory(@Param("categorie") String categorie) {
        return service.getAllByCategory(categorie);
    }

    @PostMapping("/import")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<Excel> importExcel(@Param("excel") MultipartFile excel) throws IOException {
        List<Excel> excels = ExcelConfig.importExcel(excel);
        if (excels == null) throw new NotFoundException("La liste est vide !");
        for (Excel incommingExcel :
                excels) {
            superAddInfraction(
                    incommingExcel.getDescription(),
                    incommingExcel.getReference(),
                    incommingExcel.getCategory1(),
                    incommingExcel.getCurrency1(),
                    incommingExcel.getAmount1(),
                    incommingExcel.getCategory2(),
                    incommingExcel.getCurrency2(),
                    incommingExcel.getAmount2(),
                    null,
                    null);
        }
        return excels;
    }

}

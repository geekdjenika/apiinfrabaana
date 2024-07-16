package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.Fine;
import ml.geekdjenika.apiinfrabaana.Model.Amount;
import ml.geekdjenika.apiinfrabaana.Model.Vocal;
import ml.geekdjenika.apiinfrabaana.Repository.*;
import ml.geekdjenika.apiinfrabaana.Service.fine.FineService;
import ml.geekdjenika.apiinfrabaana.Service.amount.AmountService;
import ml.geekdjenika.apiinfrabaana.Service.vocal.VocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/amende")
@CrossOrigin(origins = "*", maxAge = 3600)
@ToString
public class FineController {

    @Autowired
    private AmountService amountService;
    @Autowired
    private FineService fineService;
    @Autowired
    private AmountRepository amountRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VocalService vocalService;
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private InfringementRepository infringementRepository;
    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private LanguageRepository languageRepository;
    VocalController vocalController = new VocalController(vocalService, tipRepository, infringementRepository, fineRepository, amountRepository, languageRepository);

    @PostMapping("/montant/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Amount addMontant(@RequestBody Amount amount) {
        Amount amount1 = amountService.addAmount(amount);
        return amount1;
    }

    @GetMapping("/montant/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Amount getMontant(@PathVariable long id) {
        Amount amount1 = amountService.getAmount(id);
        return amount1;
    }

    @GetMapping("/montant/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Amount> getAll() {
        return amountService.getAllAmount();
    }

    @PutMapping("/montant/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String update(@RequestBody Amount amount, @PathVariable long id) {
        Amount amount1 = amountService.updateAmount(amount,id).get();
        return amount1.getAmount() + " " + amount1.getCurrency();
    }

    @DeleteMapping("/montant/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable long id) {
        amountService.deleteMontant(id);
        return "Montant supprimé avec succès !";
    }

    //###########################AMENDE#################################
    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Fine addFine(
            @Param("type") String type,
            @Param("montant") long montant,
            @Param("file")MultipartFile file,
            @Param("langue") String langue) throws IOException {
        Fine fine = new Fine();
        if (categoryRepository.findByCategory(type)!=null) fine.setCategory(categoryRepository.findByCategory(type));
        if (amountRepository.findByAmount(montant) != null) fine.setAmount(amountRepository.findByAmount(montant));
        else {
            Amount amount1 = amountService.addAmount(new Amount("FCFA",montant));
            fine.setAmount(amount1);
        }
        fine = fineService.addFine(fine);

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
            vocal.setFine(fine);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }


        return fine;
    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Fine getFine(@PathVariable long id) {
        return fineService.getFine(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Fine> getAllFine() {
        return fineService.getAllFine();
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Fine updateFine(
            @Param("type") String type,
            @Param("montant") String montant,
            @PathVariable long id,
            @Param("file")MultipartFile file,
            @Param("langue") String langue) throws IOException {
        Fine fine = fineRepository.findById(id).get();
        if (categoryRepository.findByCategory(type) != null) fine.setCategory(categoryRepository.findByCategory(type));;
        if (montant != null) fine.setAmount(amountRepository.findByAmount(Long.parseLong(montant)));
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
            vocal.setFine(fine);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }
        return fineService.updateFine(fine,id).get();
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteFine(@PathVariable long id) {
        fineService.deleteFine(id);
        return "Amende supprimée avec succès !";
    }

}

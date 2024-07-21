package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.configs.Audio;
import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.models.Fine;
import ml.geekdjenika.apiinfrabaana.models.Amount;
import ml.geekdjenika.apiinfrabaana.models.Vocal;
import ml.geekdjenika.apiinfrabaana.repositories.*;
import ml.geekdjenika.apiinfrabaana.services.amount.AmountServiceImpl;
import ml.geekdjenika.apiinfrabaana.services.fine.FineService;
import ml.geekdjenika.apiinfrabaana.services.amount.AmountService;
import ml.geekdjenika.apiinfrabaana.services.vocal.VocalService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fine")
@CrossOrigin
@RequiredArgsConstructor
@ToString
public class FineController {

    private final AmountService amountService;
    private final FineService service;
    private final AmountRepository amountRepository;
    private final CategoryRepository categoryRepository;
    private final VocalService vocalService;
    private final TipRepository tipRepository;
    private final InfringementRepository infringementRepository;
    private final FineRepository fineRepository;
    private final LanguageRepository languageRepository;
    VocalController vocalController = new VocalController(vocalService, tipRepository, infringementRepository, fineRepository, amountRepository, languageRepository);
    private final AmountServiceImpl amountServiceImpl;

    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Fine addFine(
            @Param("type") String type,
            @Param("amount") long amount,
            @Param("file")MultipartFile file,
            @Param("language") String language) throws IOException {
        Fine fine = new Fine();
        if (categoryRepository.findByCategory(type)!=null) fine.setCategory(categoryRepository.findByCategory(type));
        if (amountRepository.findByAmount(amount) != null) fine.setAmount(amountRepository.findByAmount(amount));
        else {
            AmountResponse amount1 = amountService.save(new Amount("FCFA",amount));
            fine.setAmount(amountRepository.findById(amount1.getId()).orElse(null));
        }
        fine = service.addFine(fine);

        if (file != null) {
            //Vocal
            String uploadDir = Audio.SOURCE_DIR+"aud";//System.getProperty("user.dir") + "/assets/aud";
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            Audio.saveAudio(uploadDir, convFile);
            Vocal vocal = new Vocal();
            if (languageRepository.findByLabel(language) != null) vocal.setLanguage(languageRepository.findByLabel(language));
            vocal.setFine(fine);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }


        return fine;
    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Fine getFine(@PathVariable long id) {
        return service.getFine(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Fine> getAllFine() {
        return service.getAllFine();
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
        return service.updateFine(fine,id).get();
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteFine(@PathVariable long id) {
        service.deleteFine(id);
        return "Amende supprimée avec succès !";
    }

}

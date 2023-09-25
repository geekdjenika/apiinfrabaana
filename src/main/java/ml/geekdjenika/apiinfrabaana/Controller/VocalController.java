package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.Vocal;
import ml.geekdjenika.apiinfrabaana.Repository.*;
import ml.geekdjenika.apiinfrabaana.Service.vocal.VocalService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vocal")
@ToString
public class VocalController {

    private final VocalService vocalService;
    private final TipRepository tipRepository;
    private final InfringementRepository infringementRepository;
    private final FineRepository fineRepository;
    private final AmountRepository amountRepository;
    private final LanguageRepository languageRepository;

    public VocalController(VocalService vocalService, TipRepository tipRepository, InfringementRepository infringementRepository, FineRepository fineRepository, AmountRepository amountRepository, LanguageRepository languageRepository) {
        this.vocalService = vocalService;
        this.tipRepository = tipRepository;
        this.infringementRepository = infringementRepository;
        this.fineRepository = fineRepository;
        this.amountRepository = amountRepository;
        this.languageRepository = languageRepository;
    }

    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Vocal addVocal(
            @Param("file") MultipartFile file,
            @Param("subject") String subject,
            @Param("language") String language) throws IOException {
        String uploadDir = Audio.SOURCE_DIR+"aud";//System.getProperty("user.dir") + "/assets/aud";
        //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        Audio.saveAudio(uploadDir, convFile);
        Vocal vocal = new Vocal();
        if (languageRepository.findByLabel(language) != null) vocal.setLanguage(languageRepository.findByLabel(language));
        else vocal.setLanguage(languageRepository.findByLabel("bm"));
        if (tipRepository.findByTip(subject) != null) vocal.setTip(tipRepository.findByTip(subject));
        if (infringementRepository.findByDescription(subject) != null) vocal.setInfringement(infringementRepository.findByDescription(subject));
        if (subject != null)
        vocal.setVocal(file.getOriginalFilename());
        vocal = vocalService.addVocal(vocal);
        return vocal;
    }
}

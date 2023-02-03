package ml.geekdjenika.apiinfrabaana.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.Vocal;
import ml.geekdjenika.apiinfrabaana.Repository.*;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.VocalService;
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
@Api(value = "Endpoint qui permet de gérer les vocaux")
public class VocalController {

    private final VocalService vocalService;
    private final ConseilRepository conseilRepository;
    private final InfractionRepository infractionRepository;
    private final AmendeRepository amendeRepository;
    private final MontantRepository montantRepository;
    private final LangueRepository langueRepository;

    public VocalController(VocalService vocalService, ConseilRepository conseilRepository, InfractionRepository infractionRepository, AmendeRepository amendeRepository, MontantRepository montantRepository, LangueRepository langueRepository) {
        this.vocalService = vocalService;
        this.conseilRepository = conseilRepository;
        this.infractionRepository = infractionRepository;
        this.amendeRepository = amendeRepository;
        this.montantRepository = montantRepository;
        this.langueRepository = langueRepository;
    }

    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "Ajouter un vocal")
    public Vocal addVocal(
            @Param("file") MultipartFile file,
            @Param("sujet") String sujet,
            @Param("langue") String langue) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/assets/aud";
        //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        Audio.saveAudio(uploadDir, convFile);
        Vocal vocal = new Vocal();
        if (langueRepository.findByLabel(langue) != null) vocal.setLangue(langueRepository.findByLabel(langue));
        else vocal.setLangue(langueRepository.findByLabel("bm"));
        if (conseilRepository.findByConseil(sujet) != null) vocal.setConseil(conseilRepository.findByConseil(sujet));
        if (infractionRepository.findByDescription(sujet) != null) vocal.setInfraction(infractionRepository.findByDescription(sujet));
        if (sujet != null)
        if (amendeRepository.findByMontant(montantRepository.findByMontant(Long.parseLong(sujet))) != null) vocal.setAmende(amendeRepository.findByMontant(montantRepository.findByMontant(Long.parseLong(sujet))));
        vocal.setVocal(file.getOriginalFilename());
        vocal = vocalService.addVocal(vocal);
        return vocal;
    }
}

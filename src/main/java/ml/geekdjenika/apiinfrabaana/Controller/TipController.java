package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.*;
import ml.geekdjenika.apiinfrabaana.Repository.*;
import ml.geekdjenika.apiinfrabaana.Service.tip.TipService;
import ml.geekdjenika.apiinfrabaana.Service.vocal.VocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conseil")
@CrossOrigin(origins = "*", maxAge = 3600)
@ToString
public class TipController {
    @Autowired
    private InfringementRepository infringementRepository;
    @Autowired
    private TipService tipService;

    @Autowired
    private VocalService vocalService;

    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TipRepository tipRepository;

    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Tip addConseil(
            @Param("conseil") String conseil,
            @Param("infraction") String infraction,
            @Param("file") MultipartFile file,
            @Param("langue") String langue) throws IOException {
        Tip tip1 = new Tip();
        tip1.setTip(conseil);

        if (infringementRepository.findByDescription(infraction) != null) tip1.getInfringements().add(infringementRepository.findByDescription(infraction));

        tip1 = tipService.addTip(tip1);
        //Notification
        Notification notification = new Notification();
        notification.setDescription("Nouveau conseil ajouté !\n" + tip1.getTip());
        notificationRepository.save(notification);
        for (User user :
                userRepository.findAll()) {
            user.getNotifications().add(notification);
            userRepository.save(user);
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
            if (languageRepository.findByLabel(langue) != null) vocal.setLanguage(languageRepository.findByLabel(langue));
            vocal.setTip(tip1);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }

        return tip1;

    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Tip getInfraction(@PathVariable long id) {
        return tipService.getTip(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Tip> getAllInfractions() {
        List<Tip> listarenversee = tipService.getAll();
        Collections.reverse(listarenversee);
        return listarenversee;
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Tip> updateInfraction(
            @Param("tip") String tip,
            @Param("infringement") String infringement,
            @PathVariable long id,
            @Param("file")MultipartFile file,
            @Param("language") String language
            ) throws IOException {
        Tip tip1 = tipRepository.findById(id).get();
        tip1.setTip(tip);
        if (infringementRepository.findByDescription(infringement) != null) tip1.getInfringements().add(infringementRepository.findByDescription(infringement));
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
            vocal.setTip(tip1);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }
        return tipService.update(tip1,id);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteInfraction(@PathVariable long id) {
        tipService.delete(id);
        return "Conseil supprimé avec succès !";
    }

}

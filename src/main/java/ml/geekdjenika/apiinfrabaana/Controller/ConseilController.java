package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.*;
import ml.geekdjenika.apiinfrabaana.Repository.*;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.ConseilService;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.VocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conseil")
@CrossOrigin(origins = "*", maxAge = 3600)
@ToString
public class ConseilController {
    @Autowired
    private InfractionRepository infractionRepository;
    @Autowired
    private ConseilService conseilService;

    @Autowired
    private VocalService vocalService;

    @Autowired
    LangueRepository langueRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Conseil addConseil(
            @Param("conseil") String conseil,
            @Param("infraction") String infraction,
            @Param("file") MultipartFile file,
            @Param("langue") String langue) throws IOException {
        Conseil conseil1 = new Conseil();
        conseil1.setConseil(conseil);

        if (infractionRepository.findByDescription(infraction) != null) conseil1.getInfractions().add(infractionRepository.findByDescription(infraction));

        conseil1 = conseilService.addConseil(conseil1);
        //Notification
        Notification notification = new Notification();
        notification.setDescription("Nouveau conseil ajouté !\n" + conseil1.getConseil());
        notificationRepository.save(notification);
        for (Utilisateur utilisateur :
                utilisateurRepository.findAll()) {
            utilisateur.getNotifications().add(notification);
        }


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
            vocal.setConseil(conseil1);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }

        return conseil1;

    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Conseil getInfraction(@PathVariable long id) {
        return conseilService.getConseil(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Conseil> getAllInfractions() {
        return conseilService.getAll();
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Conseil> updateInfraction(
            @Param("conseil") String conseil,
            @Param("infraction") String infraction,
            @PathVariable long id,
            @Param("file")MultipartFile file,
            @Param("langue") String langue
            ) throws IOException {
        Conseil conseil1 = new Conseil();
        conseil1.setConseil(conseil);
        if (infractionRepository.findByDescription(infraction) != null) conseil1.getInfractions().add(infractionRepository.findByDescription(infraction));
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
            vocal.setConseil(conseil1);
            vocal.setVocal(file.getOriginalFilename());
            vocalService.addVocal(vocal);
        }
        return conseilService.update(conseil1,id);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteInfraction(@PathVariable long id) {
        conseilService.delete(id);
        return "Conseil supprimé avec succès !";
    }

}

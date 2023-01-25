package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Model.*;
import ml.geekdjenika.apiinfrabaana.Repository.InfractionRepository;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.ConseilService;
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
@RequestMapping("/api/conseil")
@ToString
public class ConseilController {
    private final InfractionRepository infractionRepository;
    private final ConseilService conseilService;

    public ConseilController(InfractionRepository infractionRepository, ConseilService conseilService) {
        this.infractionRepository = infractionRepository;
        this.conseilService = conseilService;
    }

    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Conseil addConseil(
            @Param("conseil") String conseil,
            @Param("infraction") String infraction,
            @Param("file") MultipartFile file) throws IOException {
        Conseil conseil1 = new Conseil();
        conseil1.setConseil(conseil);
        String uploadDir = System.getProperty("user.dir") + "/assets/aud";
        //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        Audio.saveAudio(uploadDir, convFile);
        conseil1.setAudio(file.getOriginalFilename());
        if (infractionRepository.findByDescription(infraction) != null) conseil1.getInfractions().add(infractionRepository.findByDescription(infraction));

        return conseilService.addConseil(conseil1);

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
            @PathVariable long id
            ) {
        Conseil conseil1 = new Conseil();
        conseil1.setConseil(conseil);
        if (infractionRepository.findByDescription(infraction) != null) conseil1.getInfractions().add(infractionRepository.findByDescription(infraction));
        return conseilService.update(conseil1,id);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteInfraction(@PathVariable long id) {
        conseilService.delete(id);
        return "Conseil supprimé avec succès !";
    }

}

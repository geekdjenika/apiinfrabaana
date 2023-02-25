package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Configuration.Image;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@ToString
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<Utilisateur> getAllUsers() {
        return utilisateurService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable long id) {
        utilisateurService.deleteUser(id);
        return "Supprimé avec succès !";
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Utilisateur> update(
            @Param("username") String username,
            @Param("email") String email,
            @Param("photo") MultipartFile photo,
            @PathVariable long id) throws IOException {
        String imageName = StringUtils.cleanPath(photo.getOriginalFilename());
        String uploadDir = Audio.SOURCE_DIR+"img";//System.getProperty("user.dir") + "/assets/img";
        //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
        Utilisateur utilisateur = new Utilisateur(username,email,imageName);
        Image.saveImage(uploadDir, imageName, photo);
        return utilisateurService.updateUser(utilisateur,id);
    }

    @GetMapping("/{utilisateur}")
    @PostAuthorize("hasAuthority('USER')")
    public Utilisateur getUtilisateur(@PathVariable Utilisateur utilisateur) {
        return utilisateurService.getUtilisateur(utilisateur.getId());
    }

}

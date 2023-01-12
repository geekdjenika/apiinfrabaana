package ml.geekdjenika.apiinfrabaana.Controller;

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
        Utilisateur utilisateur = new Utilisateur(username,email,imageName);
        String uploadDir = "C:\\Users\\djeni\\IdeaProjects\\apiinfrabaana\\assets\\img";
        Image.saveImage(uploadDir, imageName, photo);
        return utilisateurService.updateUser(utilisateur,id);
    }

}
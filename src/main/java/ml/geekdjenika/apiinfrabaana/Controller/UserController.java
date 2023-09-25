package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Configuration.Audio;
import ml.geekdjenika.apiinfrabaana.Configuration.Image;
import ml.geekdjenika.apiinfrabaana.Model.User;
import ml.geekdjenika.apiinfrabaana.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@ToString
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<User> getAllUsers() {
        List<User> listarenversee = userService.getAllUsers();
        Collections.reverse(listarenversee);
        return listarenversee;
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "Supprimé avec succès !";
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<User> update(
            @Param("username") String username,
            @Param("email") String email,
            @Param("photo") MultipartFile photo,
            @PathVariable long id) throws IOException {
        String imageName = StringUtils.cleanPath(photo.getOriginalFilename());
        String uploadDir = Audio.SOURCE_DIR+"img";//System.getProperty("user.dir") + "/assets/img";
        //String uploadDir = System.getProperty("java.io.tmpdir") + "assets/aud"; //Pour heroku
        User user = new User(username,email,imageName);
        Image.saveImage(uploadDir, imageName, photo);
        Image.saveImage("C:\\Users\\djeni\\Documents\\infradashbord\\src\\assets\\img", imageName, photo);
        return userService.updateUser(user,id);
    }

    @GetMapping("/{utilisateur}")
    @PostAuthorize("hasAuthority('USER')")
    public User getUtilisateur(@PathVariable User user) {
        return userService.getUser(user.getId());
    }

    @PutMapping("/admin/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public User rendreAdmin(@PathVariable long id) {
        return userService.makeAdmin(id);
    }

}
package ml.geekdjenika.apiinfrabaana.Controller;

import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import ml.geekdjenika.apiinfrabaana.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilisateurController {

    private static UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService us) {
        utilisateurService = us;
    }

    @PostMapping("/signup")
    public Utilisateur saveUser(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.signUp(utilisateur);
    }

    @PostMapping("/signin")
    public Utilisateur signIn(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.signIn(utilisateur);
    }

    @PostMapping("/edituser/{id}")
    public Utilisateur updateUser(@RequestBody Utilisateur utilisateur, @PathVariable long id) {
        return utilisateurService.updateUser(utilisateur, id);
    }

    @DeleteMapping("/deleteuser")
    public String deleteUser(@PathVariable long id) {
        return utilisateurService.deleteUser(id);
    }

    @PostMapping("/roletouser")
    public void addRoleToUser(@RequestParam String username, @RequestParam String rolename) {
        utilisateurService.addRoleToUser(username, rolename);
    }

}

package ml.geekdjenika.apiinfrabaana.Service;

import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public interface UtilisateurService {

    Utilisateur signUp(Utilisateur utilisateur);

    Utilisateur updateUser(Utilisateur utilisateur, long id);

    String deleteUser(long id);

    void addRoleToUser(String username, String rolename);

    Utilisateur signIn(Utilisateur utilisateur);
}

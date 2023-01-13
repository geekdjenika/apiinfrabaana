package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    Optional<Utilisateur> updateUser(Utilisateur utilisateur, long id);

    Utilisateur getUser(long id);

    void deleteUser(long id);

    List<Utilisateur> getAllUsers();

}

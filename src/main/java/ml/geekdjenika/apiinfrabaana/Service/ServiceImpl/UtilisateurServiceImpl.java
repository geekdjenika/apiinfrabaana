package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import ml.geekdjenika.apiinfrabaana.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public Optional<Utilisateur> updateUser(Utilisateur utilisateur, long id) {
        return utilisateurRepository.findById(id).map(
                utilisateur1 -> {
                    utilisateur1.setUsername(utilisateur.getUsername());
                    utilisateur1.setEmail(utilisateur.getEmail());
                    if (!utilisateur.getImage().isEmpty()) utilisateur1.setImage(utilisateur.getImage());
                    return utilisateurRepository.save(utilisateur1);
                });
    }

    @Override
    public Utilisateur getUser(long id) {
        return utilisateurRepository.findById(id).get();
    }

    @Override
    public void deleteUser(long id) {
        Utilisateur utilisateurasupprimer = utilisateurRepository.findById(id).orElse(null);
        if (utilisateurasupprimer != null) utilisateurRepository.deleteById(id);
        else throw new RuntimeException("Utilisateur non trouvé !");
    }

    @Override
    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }
}

package ml.geekdjenika.apiinfrabaana.Service.user;

import ml.geekdjenika.apiinfrabaana.enums.ERole;
import ml.geekdjenika.apiinfrabaana.Model.User;
import ml.geekdjenika.apiinfrabaana.Repository.RoleRepository;
import ml.geekdjenika.apiinfrabaana.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<User> updateUser(User user, long id) {
        return userRepository.findById(id).map(
                utilisateur1 -> {
                    utilisateur1.setUsername(user.getUsername());
                    utilisateur1.setEmail(user.getEmail());
                    utilisateur1.setRoles(user.getRoles());
                    if (!user.getImage().isEmpty()) utilisateur1.setImage(user.getImage());
                    return userRepository.save(utilisateur1);
                });
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteUser(long id) {
        User utilisateurasupprimer = userRepository.findById(id).orElse(null);
        if (utilisateurasupprimer != null) userRepository.deleteById(id);
        else throw new RuntimeException("Utilisateur non trouvé !");
    }

    @Override
    public User makeAdmin(long id) {
        User user = userRepository.findById(id).get();
        user.getRoles().add(roleRepository.findByName(ERole.ADMIN).get());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

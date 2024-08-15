package ml.geekdjenika.apiinfrabaana.services.user;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;
import ml.geekdjenika.apiinfrabaana.enums.ERole;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Role;
import ml.geekdjenika.apiinfrabaana.models.User;
import ml.geekdjenika.apiinfrabaana.repositories.RoleRepository;
import ml.geekdjenika.apiinfrabaana.repositories.UserRepository;
import ml.geekdjenika.apiinfrabaana.services.gameSession.GameSessionService;
import ml.geekdjenika.apiinfrabaana.services.notification.NotificationService;
import ml.geekdjenika.apiinfrabaana.services.role.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final NotificationService notificationService;
    private final GameSessionService gameSessionService;

    @Override
    public UserResponse update(User user) {
        User userToUpdate = repository.findById(user.getId()).orElse(null);
        if (userToUpdate == null) throw new NotFoundException("Utilisateur introuvable !");
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setRoles(user.getRoles());
        if (!user.getImage().isEmpty()) userToUpdate.setImage(user.getImage());
        return mapToResponse(repository.save(userToUpdate));
    }

    @Override
    public UserResponse findById(long id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) throw new NotFoundException("Utilisateur introuvable !");
        return mapToResponse(user);
    }

    @Override
    public void delete(long id) {
        User userToDelete = repository.findById(id).orElse(null);
        if (userToDelete == null) throw new NotFoundException("Utilisateur introuvable !");
        repository.delete(userToDelete);
    }

    @Override
    public UserResponse makeAdmin(long id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) throw new NotFoundException("Utilisateur introuvable !");
        Role roleAdmin = roleRepository.findByName(ERole.ADMIN).orElse(null);
        if (roleAdmin == null) throw new NotFoundException("Le rôle administrateur n'est pas enregistrer !");
        user.getRoles().add(roleAdmin);
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .image(user.getImage())
                .roles(roleService.mapToResponse(new ArrayList<>(user.getRoles())))
                .notifications(notificationService.mapToResponse(user.getNotifications()))
                .gameSessions(gameSessionService.mapToResponse(user.getGameSessions()))
                .build();
    }

    @Override
    public List<UserResponse> mapToResponse(List<User> users) {
        users.sort(Comparator.comparing(User::getId).reversed());
        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach(user -> userResponses.add(mapToResponse(user)));
        return userResponses;
    }
}

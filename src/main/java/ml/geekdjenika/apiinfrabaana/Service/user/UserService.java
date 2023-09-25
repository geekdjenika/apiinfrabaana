package ml.geekdjenika.apiinfrabaana.Service.user;

import ml.geekdjenika.apiinfrabaana.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> updateUser(User user, long id);

    User getUser(long id);

    void deleteUser(long id);

    User makeAdmin(long id);

    List<User> getAllUsers();

}

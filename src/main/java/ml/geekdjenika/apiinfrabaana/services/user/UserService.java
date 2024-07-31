package ml.geekdjenika.apiinfrabaana.services.user;

import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;
import ml.geekdjenika.apiinfrabaana.models.User;

import java.util.List;

public interface UserService {
    UserResponse update(User user);
    UserResponse findById(long id);
    void delete(long id);
    UserResponse makeAdmin(long id);
    List<UserResponse> findAll();
    UserResponse mapToResponse(User user);
    List<UserResponse> mapToResponse(List<User> users);
}

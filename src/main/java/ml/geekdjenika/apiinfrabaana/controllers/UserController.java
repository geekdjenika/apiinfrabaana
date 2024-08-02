package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;
import ml.geekdjenika.apiinfrabaana.models.User;
import ml.geekdjenika.apiinfrabaana.services.user.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<UserResponse> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public UserResponse update(@RequestBody User user) {
        return service.update(user);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public UserResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}/make-admin")
    @PostAuthorize("hasAuthority('ADMIN')")
    public UserResponse makeAdmin(@PathVariable long id) {
        return service.makeAdmin(id);
    }

}

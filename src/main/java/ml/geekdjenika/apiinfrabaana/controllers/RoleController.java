package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.role.RoleResponse;
import ml.geekdjenika.apiinfrabaana.services.role.RoleService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/role")
@CrossOrigin
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public RoleResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public Set<RoleResponse> findAll() {
        return service.findAll();
    }
}

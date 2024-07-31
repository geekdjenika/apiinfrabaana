package ml.geekdjenika.apiinfrabaana.services.role;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.role.RoleResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Role;
import ml.geekdjenika.apiinfrabaana.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public RoleResponse findById(long id) {
        Role role = repository.findById(id).orElse(null);
        if (role == null) throw new NotFoundException("Ce rôle n'est pas enregistrer !");
        return mapToResponse(role);
    }

    @Override
    public Set<RoleResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public RoleResponse mapToResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    @Override
    public Set<RoleResponse> mapToResponse(List<Role> roles) {
        roles.sort(Comparator.comparing(Role::getId).reversed());
        Set<RoleResponse> roleResponses = new HashSet<>();
        roles.forEach(role -> roleResponses.add(mapToResponse(role)));
        return roleResponses;
    }
}

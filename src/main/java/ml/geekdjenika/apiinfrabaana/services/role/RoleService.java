package ml.geekdjenika.apiinfrabaana.services.role;

import ml.geekdjenika.apiinfrabaana.dto.role.RoleResponse;
import ml.geekdjenika.apiinfrabaana.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    RoleResponse findById(long id);
    Set<RoleResponse> findAll();
    RoleResponse mapToResponse(Role role);
    Set<RoleResponse> mapToResponse(List<Role> roles);
}

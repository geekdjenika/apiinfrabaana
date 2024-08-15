package ml.geekdjenika.apiinfrabaana.dto.role;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.enums.ERole;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private long id;
    private ERole name;
}

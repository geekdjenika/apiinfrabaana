package ml.geekdjenika.apiinfrabaana.dto.user;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.gameSession.GameSessionResponse;
import ml.geekdjenika.apiinfrabaana.dto.notification.NotificationResponse;
import ml.geekdjenika.apiinfrabaana.dto.role.RoleResponse;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String password;

    private String image;
    private List<NotificationResponse> notifications;
    private Set<RoleResponse> roles;
    private List<GameSessionResponse> gameSessions;
}

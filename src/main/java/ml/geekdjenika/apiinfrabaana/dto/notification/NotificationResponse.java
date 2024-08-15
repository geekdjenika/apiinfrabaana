package ml.geekdjenika.apiinfrabaana.dto.notification;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private long id;
    private String description;
    private UserResponse user;
}

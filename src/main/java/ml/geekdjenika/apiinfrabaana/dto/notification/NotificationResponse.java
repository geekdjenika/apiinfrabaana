package ml.geekdjenika.apiinfrabaana.dto.notification;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private long id;
    private String description;
}

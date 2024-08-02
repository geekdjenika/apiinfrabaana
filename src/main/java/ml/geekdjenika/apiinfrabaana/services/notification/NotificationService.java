package ml.geekdjenika.apiinfrabaana.services.notification;

import ml.geekdjenika.apiinfrabaana.dto.notification.NotificationResponse;
import ml.geekdjenika.apiinfrabaana.models.Notification;

import java.util.List;

public interface NotificationService {
    NotificationResponse save(Notification notification);
    NotificationResponse findById(long id);
    List<NotificationResponse> findByUserId(long userId);
    NotificationResponse mapToResponse(Notification notification);
    List<NotificationResponse> mapToResponse(List<Notification> notifications);
}

package ml.geekdjenika.apiinfrabaana.services.notification;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.notification.NotificationResponse;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Notification;
import ml.geekdjenika.apiinfrabaana.models.User;
import ml.geekdjenika.apiinfrabaana.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public NotificationResponse findById(long id) {
        Notification notification = repository.findById(id).orElse(null);
        if (notification == null) throw new NotFoundException("Aucune notification correspondante !");
        return mapToResponse(notification);
    }

    @Override
    public List<NotificationResponse> findByUserId(long userId) {
        return mapToResponse(repository.findByUserId(userId));
    }

    @Override
    public NotificationResponse mapToResponse(Notification notification) {
        User user = notification.getUser();
        return NotificationResponse.builder()
                .id(notification.getId())
                .description(notification.getDescription())
                .user(UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .build();
    }

    @Override
    public List<NotificationResponse> mapToResponse(List<Notification> notifications) {
        notifications.sort(Comparator.comparing(Notification::getId).reversed());
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        notifications.forEach(notification -> notificationResponses.add(mapToResponse(notification)));
        return notificationResponses;
    }
}

package ml.geekdjenika.apiinfrabaana.repositories;

import ml.geekdjenika.apiinfrabaana.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(long userId);
}

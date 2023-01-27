package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

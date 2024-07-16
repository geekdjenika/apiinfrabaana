package ml.geekdjenika.apiinfrabaana.repositories;

import ml.geekdjenika.apiinfrabaana.models.Quiz;
import ml.geekdjenika.apiinfrabaana.models.GameSession;
import ml.geekdjenika.apiinfrabaana.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findByQuiz(Quiz quiz);

    List<GameSession> findByUser(User user);
}

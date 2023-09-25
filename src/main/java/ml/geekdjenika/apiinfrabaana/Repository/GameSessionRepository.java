package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Model.GameSession;
import ml.geekdjenika.apiinfrabaana.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findByQuiz(Quiz quiz);

    List<GameSession> findByUser(User user);
}

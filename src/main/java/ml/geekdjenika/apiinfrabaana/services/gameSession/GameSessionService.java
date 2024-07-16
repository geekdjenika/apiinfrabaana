package ml.geekdjenika.apiinfrabaana.services.gameSession;

import ml.geekdjenika.apiinfrabaana.models.Quiz;
import ml.geekdjenika.apiinfrabaana.models.GameSession;
import ml.geekdjenika.apiinfrabaana.models.User;

import java.util.List;

public interface GameSessionService {
    GameSession add(GameSession gameSession);
    List<GameSession> getAll();

    GameSession getTop(User user);
    GameSession getOne(long id);
    GameSession findByQuiz(Quiz quiz);
}

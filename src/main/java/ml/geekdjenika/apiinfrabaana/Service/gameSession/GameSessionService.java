package ml.geekdjenika.apiinfrabaana.Service.gameSession;

import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Model.GameSession;
import ml.geekdjenika.apiinfrabaana.Model.User;

import java.util.List;

public interface GameSessionService {
    GameSession add(GameSession gameSession);
    List<GameSession> getAll();

    GameSession getTop(User user);
    GameSession getOne(long id);
    GameSession findByQuiz(Quiz quiz);
}

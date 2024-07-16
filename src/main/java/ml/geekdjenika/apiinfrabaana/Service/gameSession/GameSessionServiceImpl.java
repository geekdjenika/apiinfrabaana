package ml.geekdjenika.apiinfrabaana.Service.gameSession;

import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Model.GameSession;
import ml.geekdjenika.apiinfrabaana.Model.User;
import ml.geekdjenika.apiinfrabaana.Repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameSessionServiceImpl implements GameSessionService {

    @Autowired
    GameSessionRepository gameSessionRepository;

    @Override
    public GameSession add(GameSession gameSession) {
        return gameSessionRepository.save(gameSession);
    }

    @Override
    public List<GameSession> getAll() {
        return gameSessionRepository.findAll();
    }

    @Override
    public GameSession getTop(User user) {
        for (int i = gameSessionRepository.findByUser(user).toArray().length - 1; i >= 0; i--) {
            return gameSessionRepository.findByUser(user).get(i);
        }
        return null;
    }

    @Override
    public GameSession getOne(long id) {
        return gameSessionRepository.findById(id).get();
    }

    @Override
    public GameSession findByQuiz(Quiz quiz) {
        return gameSessionRepository.findByQuiz(quiz);
    }
}

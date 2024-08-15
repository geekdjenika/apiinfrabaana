package ml.geekdjenika.apiinfrabaana.services.gameSession;

import ml.geekdjenika.apiinfrabaana.dto.gameSession.GameSessionResponse;
import ml.geekdjenika.apiinfrabaana.models.GameSession;

import java.util.List;

public interface GameSessionService {
    GameSessionResponse save(GameSession gameSession);
    List<GameSessionResponse> findAll();
    List<GameSessionResponse> findByUserId(long userId);
    GameSessionResponse findById(long id);
    List<GameSessionResponse> findByQuizId(long quizId);
    GameSessionResponse mapToResponse(GameSession gameSession);
    List<GameSessionResponse> mapToResponse(List<GameSession> gameSessions);
}

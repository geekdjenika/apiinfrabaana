package ml.geekdjenika.apiinfrabaana.services.gameSession;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.gameSession.GameSessionResponse;
import ml.geekdjenika.apiinfrabaana.dto.quiz.QuizResponse;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.GameSession;
import ml.geekdjenika.apiinfrabaana.repositories.GameSessionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {

    private final GameSessionRepository repository;

    @Override
    public GameSessionResponse save(GameSession gameSession) {
        return mapToResponse(repository.save(gameSession));
    }

    @Override
    public List<GameSessionResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public List<GameSessionResponse> findByUserId(long userId) {
        return mapToResponse(repository.findByUserId(userId));
    }

    @Override
    public GameSessionResponse findById(long id) {
        GameSession gameSession = repository.findById(id).orElse(null);
        if (gameSession == null) throw new NotFoundException("Session de jeu introuvable !");
        return mapToResponse(gameSession);
    }

    @Override
    public List<GameSessionResponse> findByQuizId(long quizId) {
        return mapToResponse(repository.findByQuizId(quizId));
    }

    @Override
    public GameSessionResponse mapToResponse(GameSession gameSession) {
        return GameSessionResponse.builder()
                .id(gameSession.getId())
                .date(gameSession.getDate())
                .record(gameSession.getRecord())
                .user(UserResponse.builder()
                        .id(gameSession.getUser().getId())
                        .username(gameSession.getUser().getUsername())
                        .email(gameSession.getUser().getEmail())
                        .build())
                .quiz(QuizResponse.builder()
                        .id(gameSession.getQuiz().getId())
                        .label(gameSession.getQuiz().getLabel())
                        .build())
                .build();
    }

    @Override
    public List<GameSessionResponse> mapToResponse(List<GameSession> gameSessions) {
        gameSessions.sort(Comparator.comparing(GameSession::getId).reversed());
        List<GameSessionResponse> gameSessionResponses = new ArrayList<>();
        gameSessions.forEach(gameSession -> gameSessionResponses.add(mapToResponse(gameSession)));
        return gameSessionResponses;
    }
}

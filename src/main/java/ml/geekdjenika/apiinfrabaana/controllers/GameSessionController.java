package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.gameSession.GameSessionResponse;
import ml.geekdjenika.apiinfrabaana.models.GameSession;
import ml.geekdjenika.apiinfrabaana.services.gameSession.GameSessionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-session")
@CrossOrigin
@RequiredArgsConstructor
public class GameSessionController {

    private final GameSessionService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public GameSessionResponse save(@RequestBody GameSession gameSession) {
        return service.save(gameSession);
    }

    @GetMapping("/user/{userId}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<GameSessionResponse> findByUserId(@PathVariable long userId) {
        return service.findByUserId(userId);
    }

    @GetMapping("/quiz/{quizId}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<GameSessionResponse> findByQuizId(@PathVariable long quizId) {
        return service.findByQuizId(quizId);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public GameSessionResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<GameSessionResponse> findAll() {
        return service.findAll();
    }
}

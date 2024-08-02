package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.quiz.QuizResponse;
import ml.geekdjenika.apiinfrabaana.models.Question;
import ml.geekdjenika.apiinfrabaana.models.Quiz;
import ml.geekdjenika.apiinfrabaana.services.quiz.QuizService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
@RequiredArgsConstructor
public class QuizController {
    private final QuizService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public QuizResponse save(@RequestBody Quiz quiz) {
        return service.save(quiz);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public QuizResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<QuizResponse> findAll() {
        return service.findAll();
    }

    @PostMapping("/{id}/add-question")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void addQuestion(@PathVariable long id, @RequestBody Question question) {
        service.addQuestion(id, question);
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public QuizResponse update(@RequestBody Quiz quiz){
        return service.update(quiz);
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/remove-question")
    public void removeQuestion(@PathVariable long id, @RequestBody Question question) {
        service.removeQuestion(id, question);
    }

}

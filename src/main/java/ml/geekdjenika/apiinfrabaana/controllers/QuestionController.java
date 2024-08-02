package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.question.QuestionResponse;
import ml.geekdjenika.apiinfrabaana.models.Question;
import ml.geekdjenika.apiinfrabaana.services.question.QuestionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin
@RequiredArgsConstructor
public class QuestionController {
    
    private final QuestionService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public QuestionResponse save(@RequestBody Question question) {
        return service.save(question);
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public QuestionResponse update(@RequestBody Question question) {
        return service.update(question);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public QuestionResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @PostAuthorize("hasAuthority('USER')")
    public QuestionResponse findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<QuestionResponse> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}

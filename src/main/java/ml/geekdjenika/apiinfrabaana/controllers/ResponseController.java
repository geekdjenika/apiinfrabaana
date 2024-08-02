package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.response.ResponseResponse;
import ml.geekdjenika.apiinfrabaana.models.Response;
import ml.geekdjenika.apiinfrabaana.services.response.ResponseService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/response")
@CrossOrigin
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public ResponseResponse save(@RequestBody Response response) {
        return service.save(response);
    }

    @PutMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public ResponseResponse update(@RequestBody Response response) {
        return service.update(response);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public ResponseResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/question/{questionId}")
    @PostAuthorize("hasAuthority('USER')")
    public List<ResponseResponse> findByQuestionId(@PathVariable long questionId) {
        return service.findByQuestionId(questionId);
    }

    @GetMapping("/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<ResponseResponse> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
    
}

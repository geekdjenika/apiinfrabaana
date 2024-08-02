package ml.geekdjenika.apiinfrabaana.services.response;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.question.QuestionResponse;
import ml.geekdjenika.apiinfrabaana.dto.response.ResponseResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Question;
import ml.geekdjenika.apiinfrabaana.models.Response;
import ml.geekdjenika.apiinfrabaana.repositories.ResponseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository repository;

    @Override
    public ResponseResponse save(Response response) {
        return mapToResponse(repository.save(response));
    }

    @Override
    public ResponseResponse update(Response response) {
        Response responseToUpdate = repository.findById(response.getId()).orElse(null);
        if (responseToUpdate == null) throw new NotFoundException("Réponse introuvable !");
        responseToUpdate.setName(response.getName());
        responseToUpdate.setQuestion(response.getQuestion());
        return mapToResponse(responseToUpdate);
    }

    @Override
    public List<ResponseResponse> findByQuestionId(long questionId) {
        return mapToResponse(repository.findByQuestionId(questionId));
    }

    @Override
    public ResponseResponse findById(long id) {
        Response response = repository.findById(id).orElse(null);
        if (response == null) throw new NotFoundException("Réponse introuvable !");
        return mapToResponse(response);
    }

    @Override
    public List<ResponseResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public void delete(long id) {
        Response response = repository.findById(id).orElse(null);
        if (response == null) throw new NotFoundException("Réponse introuvable !");
        repository.delete(response);
    }

    @Override
    public ResponseResponse mapToResponse(Response response) {
        Question question = response.getQuestion();
        return ResponseResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .question(QuestionResponse.builder()
                        .id(question.getId())
                        .name(question.getName())
                        .response(question.getResponse())
                        .build())
                .build();
    }

    @Override
    public List<ResponseResponse> mapToResponse(List<Response> responses) {
        responses.sort(Comparator.comparing(Response::getId).reversed());
        List<ResponseResponse> responseResponses = new ArrayList<>();
        responses.forEach(response -> responseResponses.add(mapToResponse(response)));
        return responseResponses;
    }
}

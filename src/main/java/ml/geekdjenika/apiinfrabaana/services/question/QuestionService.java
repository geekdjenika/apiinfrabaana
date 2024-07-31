package ml.geekdjenika.apiinfrabaana.services.question;

import ml.geekdjenika.apiinfrabaana.dto.question.QuestionResponse;
import ml.geekdjenika.apiinfrabaana.models.Question;

import java.util.List;

public interface QuestionService {

    List<QuestionResponse> findAll();
    QuestionResponse findByName(String name);
    QuestionResponse findById(long id);
    QuestionResponse save(Question question);
    QuestionResponse update(Question question);
    void delete(long id);
    QuestionResponse mapToResponse(Question question);
    List<QuestionResponse> mapToResponse(List<Question> questions);

}

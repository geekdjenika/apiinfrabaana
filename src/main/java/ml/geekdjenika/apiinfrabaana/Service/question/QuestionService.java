package ml.geekdjenika.apiinfrabaana.Service.question;

import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Response;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    List<Question> getAllQuestions();

    Question getQuestion(String nomquestion);

    Optional<Question> getQuestion(long id);

    Question addQuestion(Question question);

    Optional<Question> updateQuestion(Question question, long id);

    void deleteQuestion(long id);

    void addResponses(long id, List<Response> responses);



}

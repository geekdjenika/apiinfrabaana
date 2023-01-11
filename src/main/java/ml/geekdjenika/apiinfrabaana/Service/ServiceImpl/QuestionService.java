package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Reponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionService {

    List<Question> getAllQuestions();

    Question getQuestion(String nomquestion);

    Optional<Question> getQuestion(long id);

    Question addQuestion(Question question);

    Optional<Question> updateQuestion(Question question, long id);

    void deleteQuestion(long id);

    void addReponses(long id, List<Reponse> reponses);



}

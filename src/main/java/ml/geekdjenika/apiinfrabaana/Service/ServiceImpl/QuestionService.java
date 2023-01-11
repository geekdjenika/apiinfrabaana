package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Reponse;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    List<Question> getAllQuestions();

    Question getQuestion(String nomquestion);

    Question addQuestion(Question question);

    Optional<Question> updateQuestion(Question question, long id);

    void deleteQuestion(long id);

    void addReponses(long id, List<Reponse> reponses);



}

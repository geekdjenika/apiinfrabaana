package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    Quiz addQuiz(Quiz quiz);
    Quiz getQuiz(long id);
    List<Quiz> getAllQuiz();
    void addQuestionToQuiz(Question question, long id);
    void addQuestionToQuiz(String question, long id);
    void addQuestionsToQuiz(List<String> questions, long id);
    Optional<Quiz> updateQuiz(Quiz quiz, long id);
    void deleteQuiz(long id);
    void removeQuestionToQuiz(String question, long id);

}

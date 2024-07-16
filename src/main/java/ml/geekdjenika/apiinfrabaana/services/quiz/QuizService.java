package ml.geekdjenika.apiinfrabaana.services.quiz;

import ml.geekdjenika.apiinfrabaana.models.Question;
import ml.geekdjenika.apiinfrabaana.models.Quiz;

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

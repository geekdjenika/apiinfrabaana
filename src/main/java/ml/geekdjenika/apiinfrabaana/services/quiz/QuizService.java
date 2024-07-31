package ml.geekdjenika.apiinfrabaana.services.quiz;

import ml.geekdjenika.apiinfrabaana.dto.quiz.QuizResponse;
import ml.geekdjenika.apiinfrabaana.models.Question;
import ml.geekdjenika.apiinfrabaana.models.Quiz;

import java.util.List;

public interface QuizService {
    QuizResponse save(Quiz quiz);
    QuizResponse findById(long id);
    List<QuizResponse> findAll();
    void addQuestion(long id, Question question);
    QuizResponse update(Quiz quiz);
    void delete(long id);
    void removeQuestion(long id, Question question);
    QuizResponse mapToResponse(Quiz quiz);
    List<QuizResponse> mapToResponse(List<Quiz> quizList);
}

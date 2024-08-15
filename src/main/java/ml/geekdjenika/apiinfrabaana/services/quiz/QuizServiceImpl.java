package ml.geekdjenika.apiinfrabaana.services.quiz;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.question.QuestionResponse;
import ml.geekdjenika.apiinfrabaana.dto.quiz.QuizResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Question;
import ml.geekdjenika.apiinfrabaana.models.Quiz;
import ml.geekdjenika.apiinfrabaana.repositories.QuestionRepository;
import ml.geekdjenika.apiinfrabaana.repositories.QuizRepository;
import ml.geekdjenika.apiinfrabaana.services.gameSession.GameSessionService;
import ml.geekdjenika.apiinfrabaana.services.question.QuestionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService{

    private final QuizRepository repository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;
    private final GameSessionService gameSessionService;

    @Override
    public QuizResponse save(Quiz quiz) {
        return mapToResponse(repository.save(quiz));
    }

    @Override
    public QuizResponse findById(long id) {
        Quiz quiz = repository.findById(id).orElse(null);
        if (quiz == null) throw new NotFoundException("Quiz introuvable !");
        return mapToResponse(quiz);
    }

    @Override
    public List<QuizResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public void addQuestion(long id, Question question) {
        Quiz quiz = repository.findById(id).orElse(null);
        if (quiz == null) throw new NotFoundException("Quiz introuvable !");
        QuestionResponse questionResponse = questionService.save(question);
        quiz.getQuestions().add(questionRepository.findByName(questionResponse.getName()));
    }

    @Override
    public QuizResponse update(Quiz quiz) {
        Quiz quizToUpdate = repository.findById(quiz.getId()).orElse(null);
        if (quizToUpdate == null) throw new NotFoundException("Quiz introuvable !");
        quizToUpdate.setLabel(quiz.getLabel());
        if (quiz.getQuestions() != null) {
            quizToUpdate.getQuestions().clear();
            quiz.getQuestions().forEach(question -> quizToUpdate.getQuestions().add(question));
        }
        if (quiz.getGameSessions() != null) {
            quizToUpdate.getGameSessions().clear();
            quiz.getGameSessions().forEach(gameSession -> quizToUpdate.getGameSessions().add(gameSession));
        }
        return mapToResponse(quizToUpdate);
    }

    @Override
    public void delete(long id) {
        Quiz quiz = repository.findById(id).orElse(null);
        if (quiz == null) throw new NotFoundException("Quiz introuvable !");
        repository.delete(quiz);
    }

    @Override
    public void removeQuestion(long id, Question question) {
        Quiz quiz = repository.findById(id).orElse(null);
        if (quiz == null) throw new NotFoundException("Quiz introuvable !");
        quiz.getQuestions().remove(question);
    }

    @Override
    public QuizResponse mapToResponse(Quiz quiz) {
        return QuizResponse.builder()
                .id(quiz.getId())
                .label(quiz.getLabel())
                .questions(questionService.mapToResponse(quiz.getQuestions()))
                .gameSessions(gameSessionService.mapToResponse(quiz.getGameSessions()))
                .build();
    }

    @Override
    public List<QuizResponse> mapToResponse(List<Quiz> quizList) {
        quizList.sort(Comparator.comparing(Quiz::getId).reversed());
        List<QuizResponse> quizResponses = new ArrayList<>();
        quizList.forEach(quiz -> quizResponses.add(mapToResponse(quiz)));
        return quizResponses;
    }
}

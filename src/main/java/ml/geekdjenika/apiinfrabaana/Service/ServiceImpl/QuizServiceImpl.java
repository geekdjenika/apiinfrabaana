package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Repository.QuestionRepository;
import ml.geekdjenika.apiinfrabaana.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizServiceImpl implements QuizService{

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuiz(long id) {
        return quizRepository.findById(id).get();
    }

    @Override
    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    @Override
    public void addQuestionToQuiz(Question question, long id) {

        Quiz quiz = quizRepository.findById(id).get();
        Question savedquestion = questionRepository.save(question);
        quiz.getQuestions().add(savedquestion);

    }

    @Override
    public void addQuestionToQuiz(String question, long id) {
        Quiz quiz = quizRepository.findById(id).get();
        Question questionaajt = questionRepository.findByQuestion(question);
        quiz.getQuestions().add(questionaajt);
    }

    @Override
    public void addQuestionsToQuiz(List<String> questions, long id) {
        Quiz quiz = quizRepository.findById(id).get();
        for (String question :
                questions) {
            quiz.getQuestions().add(questionRepository.findByQuestion(question));
        }
    }

    @Override
    public Optional<Quiz> updateQuiz(Quiz quiz, long id) {
        return quizRepository.findById(id).map(
                quiz1 -> {
                    quiz1.setLabel(quiz.getLabel());
                    for (Question question :
                            quiz.getQuestions()) {
                        quiz1.getQuestions().add(question);
                    }
                    return quizRepository.save(quiz1);
                }
        );
    }

    @Override
    public void deleteQuiz(long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public void removeQuestionToQuiz(String question, long id) {
        Question questionaenlever = questionRepository.findByQuestion(question);
        Quiz quiz = quizRepository.findById(id).get();
        quiz.getQuestions().remove(questionaenlever);
    }
}

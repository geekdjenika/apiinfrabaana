package ml.geekdjenika.apiinfrabaana.Service.question;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Response;
import ml.geekdjenika.apiinfrabaana.Repository.QuestionRepository;
import ml.geekdjenika.apiinfrabaana.Repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ToString
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ResponseRepository responseRepository;

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestion(String question) {
        return questionRepository.findByQuestion(question);
    }

    @Override
    public Optional<Question> getQuestion(long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> updateQuestion(Question question, long id) {
        List<Response> badResponses = question.getBadResponses();
        List<Response> reponsesexistantes = responseRepository.findByQuestion(questionRepository.findById(id).get());
        return questionRepository.findById(id).map(
                question1 -> {
                    if (!question.getQuestion().isEmpty()) question1.setQuestion(question.getQuestion());
                    if (!question.getReponse().isEmpty()) question1.setReponse(question.getReponse());
                    if (!(badResponses == null)) {
                        responseRepository.deleteAll(reponsesexistantes);
                        for (Response response :
                                badResponses) {
                            response.setQuestion(new Question(id));
                            responseRepository.save(response);
                            question1.getBadResponses().add(response);
                        }

                    }

                    return questionRepository.save(question1);
                }
        );
    }

    @Override
    public void deleteQuestion(long id) {
        Question questionasupprimer = questionRepository.findById(id).orElse(null);
        if (questionasupprimer != null) questionRepository.deleteById(id);
        else throw new RuntimeException("Question inexistante !");
    }

    @Override
    public void addResponses(long id, List<Response> responses) {
        Question question = questionRepository.findById(id).orElseThrow();
        for (Response response :
                responses) {
            responseRepository.save(response);
            question.getBadResponses().add(response);
        }
        //question.setMauvaisesReponses(reponses);
        questionRepository.save(question);
    }
}

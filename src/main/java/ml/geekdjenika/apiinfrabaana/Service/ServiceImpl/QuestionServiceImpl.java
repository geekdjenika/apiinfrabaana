package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Reponse;
import ml.geekdjenika.apiinfrabaana.Repository.QuestionRepository;
import ml.geekdjenika.apiinfrabaana.Repository.ReponseRepository;
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
    ReponseRepository reponseRepository;

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
        List<Reponse> mauvaisesreponses = question.getMauvaisesReponses();
        List<Reponse> reponsesexistantes = reponseRepository.findByQuestion(questionRepository.findById(id).get());
        return questionRepository.findById(id).map(
                question1 -> {
                    if (!question.getQuestion().isEmpty()) question1.setQuestion(question.getQuestion());
                    if (!question.getReponse().isEmpty()) question1.setReponse(question.getReponse());
                    if (!(mauvaisesreponses == null)) {
                        reponseRepository.deleteAll(reponsesexistantes);
                        for (Reponse reponse :
                                mauvaisesreponses) {
                            reponse.setQuestion(new Question(id));
                            reponseRepository.save(reponse);
                            question1.getMauvaisesReponses().add(reponse);
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
    public void addReponses(long id, List<Reponse> reponses) {
        Question question = questionRepository.findById(id).orElseThrow();
        for (Reponse reponse:
             reponses) {
            reponseRepository.save(reponse);
            question.getMauvaisesReponses().add(reponse);
        }
        //question.setMauvaisesReponses(reponses);
        questionRepository.save(question);
    }
}

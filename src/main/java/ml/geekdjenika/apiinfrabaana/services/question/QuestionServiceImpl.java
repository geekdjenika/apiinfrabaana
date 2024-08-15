package ml.geekdjenika.apiinfrabaana.services.question;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.dto.question.QuestionResponse;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;
import ml.geekdjenika.apiinfrabaana.exceptions.NotFoundException;
import ml.geekdjenika.apiinfrabaana.models.Question;
import ml.geekdjenika.apiinfrabaana.models.User;
import ml.geekdjenika.apiinfrabaana.repositories.QuestionRepository;
import ml.geekdjenika.apiinfrabaana.repositories.ResponseRepository;
import ml.geekdjenika.apiinfrabaana.services.response.ResponseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ToString
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository repository;
    private final ResponseRepository responseRepository;
    private final ResponseService responseService;

    @Override
    public List<QuestionResponse> findAll() {
        return mapToResponse(repository.findAll());
    }

    @Override
    public QuestionResponse findByName(String name) {
        return mapToResponse(repository.findByName(name));
    }

    @Override
    public QuestionResponse findById(long id) {
        Question question = repository.findById(id).orElse(null);
        if (question == null) throw new NotFoundException("Question introuvable !");
        return mapToResponse(question);
    }

    @Override
    public QuestionResponse save(Question question) {
        Question questionToSave = repository.findByName(question.getName());
        if (questionToSave != null) throw new NotFoundException("Cette question existe déjà !");
        return mapToResponse(repository.save(question));
    }

    @Override
    public QuestionResponse update(Question question) {
        Question questionToUpdate = repository.findById(question.getId()).orElse(null);
        if (questionToUpdate == null) throw new NotFoundException("Question introuvable !");
        questionToUpdate.setName(question.getName());
        questionToUpdate.setResponse(question.getResponse());
        questionToUpdate.setUser(question.getUser());
        if (question.getBadResponses() != null) {
            questionToUpdate.getBadResponses().clear();
            question.getBadResponses().forEach(badResponse -> questionToUpdate.getBadResponses().add(badResponse));
        }
        return mapToResponse(questionToUpdate);
    }

    @Override
    public void delete(long id) {
        Question question = repository.findById(id).orElse(null);
        if (question == null) throw new NotFoundException("Question introuvable !");
        repository.delete(question);
    }

    @Override
    public QuestionResponse mapToResponse(Question question) {
        User user = question.getUser();
        return QuestionResponse.builder()
                .id(question.getId())
                .name(question.getName())
                .response(question.getResponse())
                .user(UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .badResponses(responseService.mapToResponse(question.getBadResponses()))
                .build();
    }

    @Override
    public List<QuestionResponse> mapToResponse(List<Question> questions) {
        questions.sort(Comparator.comparing(Question::getId).reversed());
        List<QuestionResponse> questionResponses = new ArrayList<>();
        questions.forEach(question -> questionResponses.add(mapToResponse(question)));
        return questionResponses;
    }
}

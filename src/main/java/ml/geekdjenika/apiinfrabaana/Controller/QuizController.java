package ml.geekdjenika.apiinfrabaana.Controller;

import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Reponse;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.QuestionService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {


    private final QuestionService questionService;

    public QuizController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    @PostAuthorize("hasAuthority('USER')")
    public List<Question> getAll() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/question/{qname}")
    @PostAuthorize("hasAuthority('USER')")
    public Question getQuestion(@PathVariable String qname) {
        return questionService.getQuestion(qname);
    }

    @GetMapping("/questionid/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Optional<Question> getQuestion(@PathVariable long id) {
        return questionService.getQuestion(id);
    }

    @PostMapping("/question/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Question addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("/question/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Question> updateQuestion(@RequestBody Question question, @PathVariable long id) {
        System.out.println(question.getMauvaisesReponses());
        return questionService.updateQuestion(question, id);
    }

    @DeleteMapping("/question/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void deleteQuestion(@PathVariable long id) {
        questionService.deleteQuestion(id);
    }

    @PostMapping("/reponse/add/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public void addReponses(
            @PathVariable long id,
            @Param("reponse1") String reponse1,
            @Param("reponse2") String reponse2,
            @Param("reponse3") String reponse3) {
        List<Reponse> mauvaisesReponses = new ArrayList<>();
        mauvaisesReponses.add(new Reponse(reponse1,new Question(id)));
        mauvaisesReponses.add(new Reponse(reponse2,new Question(id)));
        mauvaisesReponses.add(new Reponse(reponse3,new Question(id)));

        /*mauvaisesReponses.add(new Reponse(reponse1));
        mauvaisesReponses.add(new Reponse(reponse2));
        mauvaisesReponses.add(new Reponse(reponse3));*/

        questionService.addReponses(id,mauvaisesReponses);

    }

}

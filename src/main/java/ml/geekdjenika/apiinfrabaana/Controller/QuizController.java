package ml.geekdjenika.apiinfrabaana.Controller;

import ml.geekdjenika.apiinfrabaana.Model.Question;
import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Model.Reponse;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import ml.geekdjenika.apiinfrabaana.Repository.UtilisateurRepository;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.QuestionService;
import ml.geekdjenika.apiinfrabaana.Service.ServiceImpl.QuizService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {


    private final QuestionService questionService;
    private final QuizService quizService;
    private final UtilisateurRepository utilisateurRepository;

    public QuizController(QuestionService questionService, QuizService quizService,
                          UtilisateurRepository utilisateurRepository) {
        this.questionService = questionService;
        this.quizService = quizService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/questions")
    @PostAuthorize("hasAuthority('USER')")
    public List<Question> getAllQuestion() {
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

    @PostMapping("/question/add/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Question addQuestion(@RequestBody Question question, @PathVariable long id) {
        question.setUtilisateur(new Utilisateur(id));
        return questionService.addQuestion(question);
    }

    @PutMapping("/question/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Question> updateQuestion(
            @Param("question") String question,
            @Param("reponse") String reponse,
            @Param("reponse1") String reponse1,
            @Param("reponse2") String reponse2,
            @Param("reponse3") String reponse3,
            @PathVariable long id) {
        Question questionamodifier = new Question();

        questionamodifier.setQuestion(question);
        questionamodifier.setReponse(reponse);
        if (reponse1.isEmpty()||reponse2.isEmpty()||reponse3.isEmpty()) questionamodifier.setMauvaisesReponses(null);
        else {
            questionamodifier.getMauvaisesReponses().add(new Reponse(reponse1));
            questionamodifier.getMauvaisesReponses().add(new Reponse(reponse2));
            questionamodifier.getMauvaisesReponses().add(new Reponse(reponse3));
        }


        return questionService.updateQuestion(questionamodifier, id);
    }

    @DeleteMapping("/question/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteQuestion(@PathVariable long id) {
        questionService.deleteQuestion(id);
        return "Question supprimée avec succès !";
    }

    @PostMapping("/reponse/add/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String addReponses(
            @PathVariable long id,
            @Param("reponse1") String reponse1,
            @Param("reponse2") String reponse2,
            @Param("reponse3") String reponse3) {
        List<Reponse> mauvaisesReponses = new ArrayList<>();
        mauvaisesReponses.add(new Reponse(reponse1,new Question(id)));
        mauvaisesReponses.add(new Reponse(reponse2,new Question(id)));
        mauvaisesReponses.add(new Reponse(reponse3,new Question(id)));

        questionService.addReponses(id,mauvaisesReponses);
        return "Réponses ajoutées avec succès !";

    }
    // #QUIZ####################    Q#U#I#Z   ####################QUIZ#
    @PostMapping("/add")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return quizService.addQuiz(quiz);
    }

    @GetMapping("/get/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping("/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<Quiz> getAllQuiz(){
        return quizService.getAllQuiz();
    }

    @PostMapping("/addqtoq/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String addQuestionToQuiz(@Param("question") String question, @PathVariable long id) {

        quizService.addQuestionToQuiz(question,id);
        return question + " ajoutée au quiz avec succès !";
    }

    @PostMapping("/addquestion/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String addQuestionToQuiz(@RequestBody Question question, @PathVariable long id) {
        quizService.addQuestionToQuiz(question,id);
        return question + " ajoutée au quiz avec succès !";
    }

    @PostMapping("/addquestions/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String addQuestionToQuiz(@Param("question1") String question1,
                                     @Param("question2") String question2,
                                     @Param("question3") String question3,
                                     @PathVariable long id) {
        quizService.addQuestionToQuiz(question1,id);
        quizService.addQuestionToQuiz(question2,id);
        quizService.addQuestionToQuiz(question3,id);

        return "Questions : [" + question1 + question2 + question3 +
                "] ajoutées au quiz avec succès !";
    }

    @PutMapping("/update/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Optional<Quiz> updateQuiz(@RequestBody Quiz quiz, @PathVariable long id){
        return quizService.updateQuiz(quiz, id);
    }

    @DeleteMapping("/delete/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String deleteQuiz(@PathVariable long id) {
        quizService.deleteQuiz(id);
        return "Quiz supprimé avec succès !";
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/removequestion/{id}")
    public String removeQuestionToQuiz(@Param("question") String question, @PathVariable long id) {
        quizService.removeQuestionToQuiz(question,id);
        return "Question supprimée de " + quizService.getQuiz(id).getLabel();
    }

}

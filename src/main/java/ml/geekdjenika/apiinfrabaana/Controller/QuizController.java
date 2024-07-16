package ml.geekdjenika.apiinfrabaana.Controller;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Model.*;
import ml.geekdjenika.apiinfrabaana.Repository.UserRepository;
import ml.geekdjenika.apiinfrabaana.Service.question.QuestionService;
import ml.geekdjenika.apiinfrabaana.Service.quiz.QuizService;
import ml.geekdjenika.apiinfrabaana.Service.gameSession.GameSessionService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(origins = "*", maxAge = 3600)
@ToString
public class QuizController {


    private final QuestionService questionService;
    private final QuizService quizService;
    private final UserRepository userRepository;

    private final GameSessionService gameSessionService;

    public QuizController(QuestionService questionService, QuizService quizService,
                          UserRepository userRepository, GameSessionService gameSessionService) {
        this.questionService = questionService;
        this.quizService = quizService;
        this.userRepository = userRepository;
        this.gameSessionService = gameSessionService;
    }

    @GetMapping("/questions")
    @PostAuthorize("hasAuthority('USER')")
    public List<Question> getAllQuestion() {
        List<Question> listarenversee = questionService.getAllQuestions();
        Collections.reverse(listarenversee);
        return listarenversee;
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
        question.setUser(new User(id));
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
        if (reponse1.isEmpty()||reponse2.isEmpty()||reponse3.isEmpty()) questionamodifier.setBadResponses(null);
        else {
            questionamodifier.getBadResponses().add(new Response(reponse1));
            questionamodifier.getBadResponses().add(new Response(reponse2));
            questionamodifier.getBadResponses().add(new Response(reponse3));
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
        List<Response> badResponses = new ArrayList<>();
        badResponses.add(new Response(reponse1,new Question(id)));
        badResponses.add(new Response(reponse2,new Question(id)));
        badResponses.add(new Response(reponse3,new Question(id)));

        questionService.addResponses(id, badResponses);
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
        List<Quiz> listarenversee = quizService.getAllQuiz();
        Collections.reverse(listarenversee);
        return listarenversee;
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
    public String addQuestionsToQuiz(@Param("question1") String question1,
                                     @Param("question2") String question2,
                                     @Param("question3") String question3,
                                     @PathVariable long id) {
        if (!quizService.getQuiz(id).getQuestions().contains(questionService.getQuestion(question1))) quizService.addQuestionToQuiz(question1,id);
        if (!quizService.getQuiz(id).getQuestions().contains(questionService.getQuestion(question2))) quizService.addQuestionToQuiz(question2,id);
        if (!quizService.getQuiz(id).getQuestions().contains(questionService.getQuestion(question3))) quizService.addQuestionToQuiz(question3,id);

        return "Questions : [" + question1 + question2 + question3 +
                "] ajoutées au quiz avec succès !";
    }

    @PostMapping("/addquestion/super/{id}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Question superAddQuestion(@Param("question") String question,
                                     @Param("reponse") String reponse,
                                     @Param("mreponse1") String mreponse1,
                                     @Param("mreponse2") String mreponse2,
                                     @Param("mreponse3") String mreponse3,
                                     @PathVariable long id) {
        addQuestion(new Question(question,reponse),id);
        Question maquestion = questionService.getQuestion(question);
        addReponses(maquestion.getId(),mreponse1,mreponse2,mreponse3);
        return maquestion;
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

    //#############SessionJeu#######################SessionJeu###################SessionJeu###########################SessionJeu######################
    @PostMapping("/score/add/{quiz}/{utilisateur}")
    @PostAuthorize("hasAuthority('USER')")
    public GameSession addSessionJeu(@RequestBody GameSession gameSession, @PathVariable Quiz quiz, @PathVariable User user) {
        //Utilisateur utilisateur1 = utilisateurRepository.findById(utilisateur.getId()).get();
        gameSession.setDate(new Date());
        gameSession.setQuiz(quiz);
        gameSession.setUser(user);
        //utilisateur1.getSessionJeux().add(sessionJeu);
        //utilisateurRepository.save(utilisateur1);
        return gameSessionService.add(gameSession);
    }

    @GetMapping("/score/get/all")
    @PostAuthorize("hasAuthority('USER')")
    public List<GameSession> getAllSessionJeu() {
        return gameSessionService.getAll();
    }

    @GetMapping("/score/get/{utilisateur}")
    @PostAuthorize("hasAuthority('USER')")
    public GameSession getSessionJeuByUser(@PathVariable User user) {
        return gameSessionService.getTop(user);
    }

}

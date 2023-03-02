package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Model.SessionJeu;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;

import java.util.List;

public interface SessionJeuService {
    SessionJeu add(SessionJeu sessionJeu);
    List<SessionJeu> getAll();

    SessionJeu getTop(Utilisateur utilisateur);
    SessionJeu getOne(long id);
    SessionJeu findByQuiz(Quiz quiz);
}

package ml.geekdjenika.apiinfrabaana.Service.ServiceImpl;

import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Model.SessionJeu;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import ml.geekdjenika.apiinfrabaana.Repository.SessionJeuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionJeuServiceImpl implements SessionJeuService{

    @Autowired
    SessionJeuRepository sessionJeuRepository;

    @Override
    public SessionJeu add(SessionJeu sessionJeu) {
        return sessionJeuRepository.save(sessionJeu);
    }

    @Override
    public List<SessionJeu> getAll() {
        return sessionJeuRepository.findAll();
    }

    @Override
    public SessionJeu getTop(Utilisateur utilisateur) {
        for (int i = sessionJeuRepository.findByUtilisateur(utilisateur).toArray().length - 1; i >= 0; i--) {
            return sessionJeuRepository.findByUtilisateur(utilisateur).get(i);
        }
        return null;
    }

    @Override
    public SessionJeu getOne(long id) {
        return sessionJeuRepository.findById(id).get();
    }

    @Override
    public SessionJeu findByQuiz(Quiz quiz) {
        return sessionJeuRepository.findByQuiz(quiz);
    }
}

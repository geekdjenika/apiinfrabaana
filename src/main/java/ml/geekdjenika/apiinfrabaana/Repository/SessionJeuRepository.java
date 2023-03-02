package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Quiz;
import ml.geekdjenika.apiinfrabaana.Model.SessionJeu;
import ml.geekdjenika.apiinfrabaana.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionJeuRepository extends JpaRepository<SessionJeu, Long> {
    SessionJeu findByQuiz(Quiz quiz);

    List<SessionJeu> findByUtilisateur(Utilisateur utilisateur);
}

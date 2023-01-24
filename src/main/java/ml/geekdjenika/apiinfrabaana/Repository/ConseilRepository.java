package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Conseil;
import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConseilRepository extends JpaRepository<Conseil, Long> {

    Conseil findByConseil(String conseil);

    //List<Conseil> findByInfraction(Infraction infraction);
}

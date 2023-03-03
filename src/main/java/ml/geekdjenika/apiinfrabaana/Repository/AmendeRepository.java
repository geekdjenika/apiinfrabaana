package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Amende;
import ml.geekdjenika.apiinfrabaana.Model.Categorie;
import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import ml.geekdjenika.apiinfrabaana.Model.Montant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmendeRepository extends JpaRepository<Amende, Long> {
    Amende findByCategorie(Categorie categorie);

    //Amende findByMontant(Montant montant);
    Amende findByCategorieAndMontant(Categorie categorie,Montant montant);

    List<Amende> findByInfractions(Infraction infraction);

    boolean existsByCategorieAndMontant(Categorie categorie, Montant montant);

}

package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Categorie findByCategorie(String categorie);
}

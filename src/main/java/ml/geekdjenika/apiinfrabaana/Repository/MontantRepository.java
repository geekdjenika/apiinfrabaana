package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Montant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MontantRepository extends JpaRepository<Montant, Long> {
    Montant findByMontant(long montant);
    Montant findByDevise(String devise);
}

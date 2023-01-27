package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Infraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InfraRepository {

    @Autowired
    private EntityManager em;

    public List<Infraction> findInfractionsByCategory(String category) {
        TypedQuery<Infraction> query = em.createQuery("SELECT i FROM Infraction i JOIN i.amendes a JOIN a.categorie c WHERE c.categorie = :category", Infraction.class);
        query.setParameter("category", category);
        return query.getResultList();
    }
}

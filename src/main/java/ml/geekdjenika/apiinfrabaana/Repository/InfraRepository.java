package ml.geekdjenika.apiinfrabaana.Repository;

import ml.geekdjenika.apiinfrabaana.Model.Infringement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InfraRepository {

    @Autowired
    private EntityManager em;

    public List<Infringement> findInfringementsByCategory(String category) {
        TypedQuery<Infringement> query = em.createQuery("SELECT i FROM Infraction i JOIN i.amendes a JOIN a.categorie c WHERE c.categorie = :category", Infringement.class);
        query.setParameter("category", category);
        return query.getResultList();
    }
}

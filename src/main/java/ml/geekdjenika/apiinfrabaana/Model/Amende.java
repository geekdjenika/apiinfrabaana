package ml.geekdjenika.apiinfrabaana.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Amende {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    Categorie categorie;

    @ManyToOne
    private Montant montant;

    private String audio;

    public Amende(long id) {
        this.id = id;
    }

    //String tauxamende = montant.getMontant() + " " +montant.getDevise() + " (" + categorie.getCategorie() + " )";
}

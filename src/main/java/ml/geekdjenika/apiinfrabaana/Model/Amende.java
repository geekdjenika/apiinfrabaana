package ml.geekdjenika.apiinfrabaana.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Categorie categorie;

    @ManyToOne
    private Montant montant;

    @OneToMany(mappedBy = "amende", cascade = CascadeType.REMOVE)
    private List<Vocal> vocals = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Infraction> infractions = new ArrayList<>();

    public Amende(long id) {
        this.id = id;
    }

    //String tauxamende = montant.getMontant() + " " +montant.getDevise() + " (" + categorie.getCategorie() + " )";
}

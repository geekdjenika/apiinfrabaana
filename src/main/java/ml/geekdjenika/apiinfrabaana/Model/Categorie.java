package ml.geekdjenika.apiinfrabaana.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String categorie;

    @OneToMany(mappedBy="categorie", cascade = CascadeType.REMOVE)
    private List<Amende> amendes = new ArrayList<>();

    public Categorie(String categorie) {
        this.categorie = categorie;
    }
}

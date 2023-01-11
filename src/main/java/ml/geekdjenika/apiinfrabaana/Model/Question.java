package ml.geekdjenika.apiinfrabaana.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String question;

    private String reponse;

    @OneToMany(mappedBy="question")
    private List<Reponse> mauvaisesReponses=new ArrayList<>();

    /*public <E> Question(String question, String reponse, Set<Reponse> reponses) {

    }*/

    public Question(long id) {
        this.id = id;
    }
}

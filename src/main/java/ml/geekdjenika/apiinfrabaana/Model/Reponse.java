package ml.geekdjenika.apiinfrabaana.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    String reponse;

    @JsonIgnore
    @ManyToOne
    Question question;

    public Reponse(String reponse, Question question) {
        this.reponse = reponse;
        this.question = question;
    }

    public Reponse(String reponse) {
        this.reponse = reponse;
    }
}

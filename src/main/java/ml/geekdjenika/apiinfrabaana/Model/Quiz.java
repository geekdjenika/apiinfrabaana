package ml.geekdjenika.apiinfrabaana.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String label;

    @ManyToMany
    private Collection<Question> questions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    List<GameSession> gameSessionList = new ArrayList<>();

}

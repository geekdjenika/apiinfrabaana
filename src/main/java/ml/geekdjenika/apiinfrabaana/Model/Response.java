package ml.geekdjenika.apiinfrabaana.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    String response;

    @JsonIgnore
    @ManyToOne
    Question question;

    public Response(String response, Question question) {
        this.response = response;
        this.question = question;
    }

    public Response(String response) {
        this.response = response;
    }
}

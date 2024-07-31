package ml.geekdjenika.apiinfrabaana.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String name;

    private String response;

    @OneToMany(mappedBy="question", cascade = CascadeType.REMOVE)
    private List<Response> badResponses =new ArrayList<>();

    @ManyToOne
    private User user;
}

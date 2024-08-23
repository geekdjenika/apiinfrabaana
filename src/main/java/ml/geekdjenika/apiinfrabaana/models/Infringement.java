package ml.geekdjenika.apiinfrabaana.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Infringement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(length = 1000, unique = true)
    private String description;

    private String reference;

    @ManyToMany
    private List<Fine> fines = new ArrayList<>();

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "infringement", cascade = CascadeType.REMOVE)
    private List<Vocal> vocals = new ArrayList<>();

}

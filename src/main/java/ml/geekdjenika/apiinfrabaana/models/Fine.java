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
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Category category;

    @ManyToOne
    private Amount amount;

    @OneToMany(mappedBy = "fine", cascade = CascadeType.ALL)
    private List<Vocal> vocals = new ArrayList<>();

    @ManyToMany(mappedBy = "fines")
    private List<Infringement> infringements = new ArrayList<>();
}

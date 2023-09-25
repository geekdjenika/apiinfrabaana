package ml.geekdjenika.apiinfrabaana.Model;

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
    Category category;

    @ManyToOne
    private Amount amount;

    @OneToMany(mappedBy = "fine", cascade = CascadeType.REMOVE)
    private List<Vocal> vocals = new ArrayList<>();

    @ManyToMany
    private List<Infringement> infringements = new ArrayList<>();

    public Fine(long id) {
        this.id = id;
    }
}

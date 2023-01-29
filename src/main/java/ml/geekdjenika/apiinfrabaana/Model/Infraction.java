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
public class Infraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String description;

    private String reference;

    @OneToMany(mappedBy = "infraction")
    private List<Vocal> vocals = new ArrayList<>();

    @ManyToMany(mappedBy = "infractions")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Amende> amendes = new ArrayList<>();

    @ManyToOne
    private  Utilisateur utilisateur;
}

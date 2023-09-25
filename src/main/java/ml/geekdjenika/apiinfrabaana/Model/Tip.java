package ml.geekdjenika.apiinfrabaana.Model;

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
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(length = 1000)
    private String tip;

    @OneToMany(mappedBy = "tip", cascade = CascadeType.REMOVE)
    private List<Vocal> vocals = new ArrayList<>();

    @ManyToMany
    Collection<Infringement> infringements = new ArrayList<>();

}

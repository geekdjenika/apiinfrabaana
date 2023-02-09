package ml.geekdjenika.apiinfrabaana.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SessionJeu {
    @Id
    private long id;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Quiz quiz;

    private Date date;

    private long points;

}

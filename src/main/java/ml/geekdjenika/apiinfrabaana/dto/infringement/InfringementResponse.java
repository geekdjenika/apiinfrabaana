package ml.geekdjenika.apiinfrabaana.dto.infringement;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.models.Vocal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfringementResponse {
    private long id;
    private String description;
    private String reference;
    private List<VocalResponse> vocals;
}

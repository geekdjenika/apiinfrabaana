package ml.geekdjenika.apiinfrabaana.dto.fine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.models.Amount;
import ml.geekdjenika.apiinfrabaana.models.Category;
import ml.geekdjenika.apiinfrabaana.models.Infringement;
import ml.geekdjenika.apiinfrabaana.models.Vocal;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FineResponse {
    private long id;
    CategoryResponse category;
    private AmountResponse amount;
    private List<VocalResponse> vocals;
    private List<InfringementResponse> infringements;
}

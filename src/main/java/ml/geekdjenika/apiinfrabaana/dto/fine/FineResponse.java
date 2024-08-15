package ml.geekdjenika.apiinfrabaana.dto.fine;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.amount.AmountResponse;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.dto.vocal.VocalResponse;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FineResponse {
    private long id;
    private CategoryResponse category;
    private AmountResponse amount;
    private List<VocalResponse> vocals;
    private List<InfringementResponse> infringements;
}

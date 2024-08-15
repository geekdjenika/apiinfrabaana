package ml.geekdjenika.apiinfrabaana.dto.infringement;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.category.CategoryResponse;
import ml.geekdjenika.apiinfrabaana.dto.vocal.VocalResponse;

import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfringementResponse {
    private long id;
    private String description;
    private String reference;
    private CategoryResponse category;
    private List<VocalResponse> vocals;
}

package ml.geekdjenika.apiinfrabaana.dto.category;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.fine.FineResponse;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private long id;
    private String name;
    private List<FineResponse> fines;
}

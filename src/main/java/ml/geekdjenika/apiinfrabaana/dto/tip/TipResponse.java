package ml.geekdjenika.apiinfrabaana.dto.tip;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.dto.vocal.VocalResponse;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipResponse {
    private long id;
    private String tip;
    private List<VocalResponse> vocals;
    private List<InfringementResponse> infringements;
}

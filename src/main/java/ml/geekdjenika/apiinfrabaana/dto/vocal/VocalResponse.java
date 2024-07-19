package ml.geekdjenika.apiinfrabaana.dto.vocal;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.fine.FineResponse;
import ml.geekdjenika.apiinfrabaana.dto.infringement.InfringementResponse;
import ml.geekdjenika.apiinfrabaana.dto.language.LanguageResponse;
import ml.geekdjenika.apiinfrabaana.dto.tip.TipResponse;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VocalResponse {
    private long id;
    private String vocal;
    private LanguageResponse language;
    private TipResponse tip;
    private InfringementResponse infringement;
    private FineResponse fine;
}

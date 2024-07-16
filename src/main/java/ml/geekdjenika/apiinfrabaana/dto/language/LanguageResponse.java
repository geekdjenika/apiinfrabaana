package ml.geekdjenika.apiinfrabaana.dto.language;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LanguageResponse {
    private long id;
    private String label;
}

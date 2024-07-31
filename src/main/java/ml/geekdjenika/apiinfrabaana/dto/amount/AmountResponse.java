package ml.geekdjenika.apiinfrabaana.dto.amount;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmountResponse {
    private long id;
    private long value;
    private String currency;
}

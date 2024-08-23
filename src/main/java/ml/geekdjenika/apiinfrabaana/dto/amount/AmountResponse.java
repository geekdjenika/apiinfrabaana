package ml.geekdjenika.apiinfrabaana.dto.amount;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmountResponse {
    private long id;
    private double value;
    private String currency;
}

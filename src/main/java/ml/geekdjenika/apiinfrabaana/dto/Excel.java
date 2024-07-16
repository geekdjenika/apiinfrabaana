package ml.geekdjenika.apiinfrabaana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Excel {
    String description;
    String reference;
    String category1;
    String amount1;
    String currency1;
    String category2;
    String amount2;
    String currency2;
}

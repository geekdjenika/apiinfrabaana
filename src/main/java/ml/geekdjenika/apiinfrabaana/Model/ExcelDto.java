package ml.geekdjenika.apiinfrabaana.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcelDto {
    String description;
    String reference;
    String categorie1;
    String montant1;
    String devise1;
    String categorie2;
    String montant2;
    String devise2;
}

package ml.geekdjenika.apiinfrabaana.dto.response;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.question.QuestionResponse;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResponse {
    private long id;
    private String name;
    private QuestionResponse question;
}

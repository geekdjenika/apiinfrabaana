package ml.geekdjenika.apiinfrabaana.dto.question;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.models.Response;
import ml.geekdjenika.apiinfrabaana.models.User;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private long id;
    private String question;
    private String response;
    private List<ResponseResponse> badResponses;
    private UserResponse user;
}

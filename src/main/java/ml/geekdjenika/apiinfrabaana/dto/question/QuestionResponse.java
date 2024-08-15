package ml.geekdjenika.apiinfrabaana.dto.question;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.response.ResponseResponse;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private long id;
    private String name;
    private String response;
    private List<ResponseResponse> badResponses;
    private UserResponse user;
}

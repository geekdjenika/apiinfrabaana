package ml.geekdjenika.apiinfrabaana.dto.gameSession;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ml.geekdjenika.apiinfrabaana.models.Quiz;
import ml.geekdjenika.apiinfrabaana.models.User;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameSessionResponse {
    private long id;
    private UserResponse user;
    private QuizResponse quiz;
    private LocalDateTime date;
    private long record;
}

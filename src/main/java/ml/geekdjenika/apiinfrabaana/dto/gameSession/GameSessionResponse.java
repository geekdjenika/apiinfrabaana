package ml.geekdjenika.apiinfrabaana.dto.gameSession;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.quiz.QuizResponse;
import ml.geekdjenika.apiinfrabaana.dto.user.UserResponse;
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

package ml.geekdjenika.apiinfrabaana.dto.quiz;

import lombok.*;
import ml.geekdjenika.apiinfrabaana.dto.gameSession.GameSessionResponse;
import ml.geekdjenika.apiinfrabaana.dto.question.QuestionResponse;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    private long id;
    private String label;
    private List<QuestionResponse> questions;
    private List<GameSessionResponse> gameSessions;
}

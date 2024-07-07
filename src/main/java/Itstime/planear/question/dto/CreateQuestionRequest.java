package Itstime.planear.question.dto;

import java.time.LocalDateTime;

public record CreateQuestionRequest(
        String content,
        LocalDateTime exposeStartAt,
        LocalDateTime exposeEndAt
) {
}

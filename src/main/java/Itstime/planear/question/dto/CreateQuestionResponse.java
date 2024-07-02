package Itstime.planear.question.dto;

import Itstime.planear.question.domain.Question;

import java.time.LocalDateTime;

public record CreateQuestionResponse(
        Long id,
        String content,
        LocalDateTime exposeStartAt,
        LocalDateTime exposeEndAt) {
    public static CreateQuestionResponse from(Question question) {
        return new CreateQuestionResponse(
                question.getId(),
                question.getContent(),
                question.getExposeStartAt(),
                question.getExposeEndAt()
        );
    }
}

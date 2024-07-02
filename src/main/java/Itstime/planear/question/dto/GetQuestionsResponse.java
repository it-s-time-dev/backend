package Itstime.planear.question.dto;

import Itstime.planear.question.domain.Question;

import java.util.List;

public record GetQuestionsResponse(
        List<GetQuestionResponse> questions
) {
    public record GetQuestionResponse(
            Long id,
            String content
    ) {

        public static GetQuestionResponse from(Question question) {
            return new GetQuestionResponse(question.getId(), question.getContent());
        }
    }
}

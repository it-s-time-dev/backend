package Itstime.planear.question.controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.question.dto.CreateQuestionRequest;
import Itstime.planear.question.dto.CreateQuestionResponse;
import Itstime.planear.question.dto.GetQuestionsResponse;
import Itstime.planear.question.service.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "소셜 질문 컨트롤러", description = "소셜 질문 관련 API입니다.")
@RequiredArgsConstructor
@RestController
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/questions")
    public ApiResponse<GetQuestionsResponse> getExposedQuestions() {
        List<GetQuestionsResponse.GetQuestionResponse> response = questionService.getExposedQuestions()
                .stream()
                .map(GetQuestionsResponse.GetQuestionResponse::from)
                .toList();
        return ApiResponse.success(new GetQuestionsResponse(response));
    }

    @PostMapping("/questions")
    public ApiResponse<CreateQuestionResponse> addQuestion(@RequestBody CreateQuestionRequest request) {
        return ApiResponse.success(CreateQuestionResponse.from(questionService.addQuestion(request)));
    }
}

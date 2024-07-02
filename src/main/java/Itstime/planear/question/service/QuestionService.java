package Itstime.planear.question.service;

import Itstime.planear.question.domain.Question;
import Itstime.planear.question.domain.QuestionRepository;
import Itstime.planear.question.dto.CreateQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getExposedQuestions() {
        LocalDateTime now = LocalDateTime.now();
        return questionRepository.findAll()
                .stream()
                .filter(question -> question.isExposable(now))
                .toList();
    }

    public Question addQuestion(CreateQuestionRequest request) {
        return questionRepository.save(new Question(request.content(), request.exposeStartAt(), request.exposeEndAt()));
    }
}

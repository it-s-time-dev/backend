package Itstime.planear.statusmessage.service;

import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.question.domain.Question;
import Itstime.planear.question.domain.QuestionRepository;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Service.ScheduleService;
import Itstime.planear.statusmessage.domain.MemberQuestion;
import Itstime.planear.statusmessage.domain.MemberQuestionRepository;
import Itstime.planear.statusmessage.domain.StatusMessage;
import Itstime.planear.statusmessage.domain.StatusMessageRepository;
import Itstime.planear.statusmessage.dto.StatusCreateRequest;
import Itstime.planear.statusmessage.dto.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StatusMessageService {

    private final ScheduleService scheduleService;
    private final StatusMessageRepository statusMessageRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final MemberQuestionRepository memberQuestionRepository;

    public StatusResponse getCurrentStatus(Long memberId) {
        Optional<StatusMessage> statusMessage = statusMessageRepository.findFirstByMemberIdOrderByIdDesc(memberId);
        if (statusMessage.isEmpty()) {
            return buildTodayScheduleResponse(memberId);
        }
        return getStatusResponse(memberId, statusMessage.get());
    }

    private StatusResponse getStatusResponse(Long memberId, StatusMessage statusMessage1) {
        return switch (statusMessage1.getMessageType()) {
            case UNCOMPLETE -> uncompleteResponse(memberId);
            case TODAY_SCHEDULE -> buildTodayScheduleResponse(memberId);
            case QNA -> qnaResponse(memberId);
        };
    }

    private StatusResponse qnaResponse(Long memberId) {
        MemberQuestion memberQuestion = memberQuestionRepository.findFirstByMemberIdAndOrderByIdDesc(memberId)
                .orElseThrow(() -> new PlanearException("존재하지 않는 질문입니다.", HttpStatus.BAD_REQUEST));
        return StatusResponse.qna(
                new StatusResponse.QnaResponse(
                        memberQuestion.getQuestionContent(),
                        memberQuestion.getAnswer()
                ));
    }

    private StatusResponse uncompleteResponse(Long memberId) {
        List<ScheduleResponseDTO.ScheduleFindOneDTO> one = scheduleService.findOne(memberId, LocalDate.now());
        int uncompleteCount = (int) one.stream().filter(it -> !it.isCompletion()).count();
        int completeCount = one.size() - uncompleteCount;
        return StatusResponse.uncomplete(new StatusResponse.UncompleteStatusResponse(
                uncompleteCount,
                (int) ((double) completeCount / one.size() * 100)
        ));
    }

    private StatusResponse buildTodayScheduleResponse(Long memberId) {
        var todaySchedule = scheduleService.findOne(memberId, LocalDate.now()).stream()
                .map(it -> new StatusResponse.TodayScheduleResponse(it.getTitle(), it.isCompletion()))
                .toList();
        return StatusResponse.todaySchedule(todaySchedule);
    }

    public void createStatusMessage(StatusCreateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new PlanearException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST));
        var a = switch (request.type()) {// exhaust 를 위해서 변수에 할당
            case UNCOMPLETE -> statusMessageRepository.save(StatusMessage.uncomplete(member));
            case QNA -> {
                Question question = questionRepository.findById(request.qna().questionId()).orElseThrow(() -> new PlanearException("존재하지 않는 질문입니다.", HttpStatus.BAD_REQUEST));
                MemberQuestion memberQuestion = MemberQuestion.builder()
                        .member(member)
                        .answer(request.qna().answer())
                        .questionContent(question.getContent())
                        .build();
                memberQuestionRepository.save(memberQuestion);
                yield statusMessageRepository.save(StatusMessage.qna(member));
            }
            case TODAY_SCHEDULE -> statusMessageRepository.save(StatusMessage.todaySchedule(member));
        };
    }
}

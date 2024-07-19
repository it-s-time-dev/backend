package Itstime.planear.statusmessage.service;

import Itstime.planear.coin.domain.Coin;
import Itstime.planear.coin.domain.CoinRepository;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.question.domain.Question;
import Itstime.planear.question.domain.QuestionRepository;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Service.ScheduleService;
import Itstime.planear.statusmessage.domain.MemberQuestion;
import Itstime.planear.statusmessage.domain.MemberQuestionRepository;
import Itstime.planear.statusmessage.domain.MessageType;
import Itstime.planear.statusmessage.domain.StatusMessage;
import Itstime.planear.statusmessage.domain.StatusMessageRepository;
import Itstime.planear.statusmessage.dto.StatusCreateRequest;
import Itstime.planear.statusmessage.dto.StatusResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.metrics.data.DefaultRepositoryTagsProvider;
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
    private final CoinRepository coinRepository;

    public StatusResponse getCurrentStatus(Long memberId) {
        Optional<StatusMessage> statusMessage = statusMessageRepository.findFirstByMemberIdOrderByIdDesc(memberId);
        if (statusMessage.isEmpty()) {
            return getStatusResponse(memberId, MessageType.TODAY_SCHEDULE);
        }
        MessageType messageType = statusMessage.get().getMessageType();
        return getStatusResponse(memberId, messageType);
    }

    public StatusResponse getStatusResponse(Long memberId, MessageType messageType) {
        return switch (messageType) {
            case UNCOMPLETE, TODAY_SCHEDULE -> new StatusResponse(messageType,
                    uncompleteResponse(memberId),
                    buildTodayScheduleResponse(memberId),
                    null
            );
            case QNA -> new StatusResponse(
                    messageType,
                    uncompleteResponse(memberId),
                    buildTodayScheduleResponse(memberId),
                    qnaResponse(memberId)
            );
        };
    }

    private StatusResponse.QnaResponse qnaResponse(Long memberId) {
        MemberQuestion memberQuestion = memberQuestionRepository.findFirstByMemberIdOrderByIdDesc(memberId)
                .orElseThrow(() -> new PlanearException("존재하지 않는 질문입니다.", HttpStatus.BAD_REQUEST));
        return new StatusResponse.QnaResponse(
                memberQuestion.getQuestionContent(),
                memberQuestion.getAnswer()
        );
    }

    private StatusResponse.UncompleteStatusResponse uncompleteResponse(Long memberId) {
        List<ScheduleResponseDTO.ScheduleFindOneDTO> one = scheduleService.findOne(memberId, LocalDate.now());
        int uncompleteCount = (int) one.stream().filter(it -> !it.isCompletion()).count();
        int completeCount = one.size() - uncompleteCount;
        if (one.isEmpty()) {
            return new StatusResponse.UncompleteStatusResponse(0, 0);
        }
        return new StatusResponse.UncompleteStatusResponse(
                uncompleteCount,
                (int) ((double) completeCount / one.size() * 100)
        );
    }

    private List<StatusResponse.TodayScheduleResponse> buildTodayScheduleResponse(Long memberId) {
        return scheduleService.findOne(memberId, LocalDate.now()).stream()
                .map(it -> new StatusResponse.TodayScheduleResponse(it.getTitle(), it.isCompletion()))
                .toList();
    }

    @Transactional
    public void createStatusMessage(StatusCreateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new PlanearException("존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST));
        Coin coin = coinRepository.findByMemberId(memberId).orElseThrow(() -> new PlanearException("존재하지 않는 코인입니다.", HttpStatus.BAD_REQUEST));

        coin.updateCoinAmount(coin.getCoinAmount().add(5));
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

package Itstime.planear.statusmessage.service;

import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Service.ScheduleService;
import Itstime.planear.statusmessage.domain.StatusMessage;
import Itstime.planear.statusmessage.domain.StatusMessageRepository;
import Itstime.planear.statusmessage.dto.StatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StatusMessageService {

    private final ScheduleService scheduleService;
    private final StatusMessageRepository statusMessageRepository;

    public StatusResponse getCurrentStatus(Long memberId) {
        Optional<StatusMessage> statusMessage = statusMessageRepository.findByMemberIdOrderByIdDesc(memberId);
        if (statusMessage.isEmpty()) {
            return buildTodayScheduleResponse(memberId);
        }
        return switch (statusMessage.get().getMessageType()) {
            case UNCOMPLETE -> uncompleteResponse(memberId);
            case TODAY_SCHEDULE -> buildTodayScheduleResponse(memberId);
            case QNA -> qnaResponse(statusMessage.get());
        };
    }

    private StatusResponse qnaResponse(StatusMessage statusMessage) {
        return StatusResponse.qna(
                new StatusResponse.QnaResponse(
                        statusMessage.getQuestion(),
                        statusMessage.getAnswer()
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
}

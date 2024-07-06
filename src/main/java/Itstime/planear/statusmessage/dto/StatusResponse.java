package Itstime.planear.statusmessage.dto;

import Itstime.planear.statusmessage.domain.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StatusResponse(
        MessageType type,
        UncompleteStatusResponse uncomplete,
        List<TodayScheduleResponse> todaySchedule,
        QnaResponse qna
) {

    public static StatusResponse uncomplete(UncompleteStatusResponse uncompleteStatusResponse) {
        return new StatusResponse(MessageType.UNCOMPLETE, uncompleteStatusResponse, null, null);
    }

    public static StatusResponse todaySchedule(List<TodayScheduleResponse> todaySchedule) {
        return new StatusResponse(MessageType.TODAY_SCHEDULE, null, todaySchedule, null);
    }

    public static StatusResponse qna(QnaResponse qnaResponse) {
        return new StatusResponse(MessageType.QNA, null, null, qnaResponse);
    }

    public record UncompleteStatusResponse(
            int uncompleteCount,
            int achievementRate
    ) {
    }

    public record QnaResponse(
            String question,
            String answer
    ) {
    }

    public record TodayScheduleResponse(
            String title,
            boolean complete
    ) {
    }
}

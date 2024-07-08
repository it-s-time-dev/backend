package Itstime.planear.feed.dto;

import Itstime.planear.statusmessage.domain.MessageType;
import Itstime.planear.statusmessage.dto.StatusResponse;

import java.util.List;

public record FeedStatusMessageResponse(
        MessageType type,
        UncompleteStatusResponse uncomplete,
        List<TodayScheduleResponse> todaySchedule,
        QnaResponse qna) {

    public static FeedStatusMessageResponse from(StatusResponse statusResponse) {
        return new FeedStatusMessageResponse(statusResponse.type(),
                UncompleteStatusResponse.from(statusResponse.uncomplete()),
                TodayScheduleResponse.from(statusResponse.todaySchedule()),
                QnaResponse.from(statusResponse.qna()));
    }

    public record UncompleteStatusResponse(
            int uncompleteCount,
            int achievementRate
    ) {

        public static UncompleteStatusResponse from(StatusResponse.UncompleteStatusResponse uncompleteStatus) {
            if (uncompleteStatus == null) {
                return null;
            }
            return new UncompleteStatusResponse(uncompleteStatus.uncompleteCount(), uncompleteStatus.achievementRate());
        }
    }

    public record QnaResponse(
            String question,
            String answer
    ) {

        public static QnaResponse from(StatusResponse.QnaResponse qnaResponse) {
            if (qnaResponse == null) {
                return null;
            }
            return new QnaResponse(qnaResponse.question(), qnaResponse.answer());
        }
    }

    public record TodayScheduleResponse(
            String title,
            boolean complete
    ) {

        public static List<TodayScheduleResponse> from(List<StatusResponse.TodayScheduleResponse> todayScheduleResponses) {
            return todayScheduleResponses.stream()
                    .map(TodayScheduleResponse::from)
                    .toList();
        }

        public static TodayScheduleResponse from(StatusResponse.TodayScheduleResponse todayScheduleResponse) {
            return new TodayScheduleResponse(todayScheduleResponse.title(), todayScheduleResponse.complete());
        }
    }
}

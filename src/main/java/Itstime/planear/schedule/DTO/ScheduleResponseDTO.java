package Itstime.planear.schedule.DTO;

import Itstime.planear.schedule.Domain.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleResponseDTO {
    // 일정 완료
    @Getter
    public static class ScheduleCompleteDTO {
        private Long id;
        private String title;
        private boolean completion;

        public ScheduleCompleteDTO(Schedule schedule) {
            id = schedule.getId();
            title = schedule.getTitle();
            completion = schedule.isCompletion();
        }
    }

    // 일정 추가
    @Getter
    public static class ScheduleCreateDTO {
        private Long id;
        private String title;
        private Long categoryId;

        public ScheduleCreateDTO(Schedule schedule) {
            id = schedule.getId();
            title = schedule.getTitle();
            categoryId = schedule.getCategoryId();

        }
    }

    // 일정 수정
    @Getter
    public static class ScheduleUpdateDTO {
        private Long id;
        private String title;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate start;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate end;

        private Long categoryId;
        private String detail;

        public ScheduleUpdateDTO(Schedule schedule) {
            id = schedule.getId();
            title = schedule.getTitle();
            start = schedule.getStart();
            end = schedule.getEnd();
            categoryId = schedule.getCategoryId();
            detail = schedule.getDetail();
        }
    }

    // 먼슬리 일정 조회
    @Getter
    public static class ScheduleFindAllDTO {
        private Long id;
        private Long categoryId;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate start;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate end;
        private String title;
        private String detail;
        private boolean completion;

        public ScheduleFindAllDTO(Schedule schedule) {
            id = schedule.getId();
            categoryId = schedule.getCategoryId();
            start = schedule.getStart();
            end = schedule.getEnd();
            title = schedule.getTitle();
            detail = schedule.getDetail();
            completion = schedule.isCompletion();
        }
    }

    // 상세 일정 조회
    @Getter
    public static class ScheduleFindOneDTO {
        private Long id;
        private Long categoryId;
        private String title;
        private boolean completion;

        public ScheduleFindOneDTO(Schedule schedule) {
            id = schedule.getId();
            categoryId = schedule.getCategoryId();
            title = schedule.getTitle();
            completion = schedule.isCompletion();
        }
    }

    // 일정 삭제
    @Getter
    public static class ScheduleDeleteDTO {
        private Long id;

        public ScheduleDeleteDTO(Long id) {
            this.id = id;
        }
    }
}

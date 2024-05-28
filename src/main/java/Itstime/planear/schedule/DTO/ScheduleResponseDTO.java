package Itstime.planear.schedule.DTO;

import Itstime.planear.schedule.Domain.Category;
import Itstime.planear.schedule.Domain.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleResponseDTO {
    // 일정 완료
    @Getter
    public static class scheduleCompleteDTO {
        private Long id;
        private String title;
        private boolean completion;

        public scheduleCompleteDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();
            this.completion = schedule.isCompletion();
        }
    }
    // 일정 추가
    @Getter
    public static class ScheduleCreateDTO {
        private Long id;
        private String title;
        private Category category;

        public ScheduleCreateDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();
            this.category = schedule.getCategory();

        }
    }

    // 일정 수정
    @Getter
    public static class scheduleUpdateDTO {
        private Long id;
        private String title;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate start;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate end;

        private Long categoryId;
        private String detail;

        public scheduleUpdateDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();
            this.start = schedule.getStart();
            this.end = schedule.getEnd();
            this.categoryId = schedule.getCategory().getId();
            this.detail = schedule.getDetail();
        }
    }
}

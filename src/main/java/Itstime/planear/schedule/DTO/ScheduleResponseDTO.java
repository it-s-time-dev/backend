package Itstime.planear.schedule.DTO;

import Itstime.planear.schedule.Domain.Category;
import Itstime.planear.schedule.Domain.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleResponseDTO {
    // 일정 추가
    @Getter
    public static class ScheduleCreateDTO {
        private Long id;
        private String title;

        public ScheduleCreateDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();

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

        private Category category;
        private String detail;

        public ScheduleUpdateDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();
            this.start = schedule.getStart();
            this.end = schedule.getEnd();
            this.category = schedule.getCategory();
            this.detail = schedule.getDetail();
        }
    }
}
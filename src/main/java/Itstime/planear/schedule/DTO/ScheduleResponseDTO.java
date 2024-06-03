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
    public static class ScheduleCompleteDTO {
        private Long id;
        private String title;
        private boolean completion;
//        private int coin; //테스트

        public ScheduleCompleteDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();
            this.completion = schedule.isCompletion();
//            this.coin = schedule.getCoin().getAmount(); //테스트
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
            this.id = schedule.getId();
            this.title = schedule.getTitle();
            this.start = schedule.getStart();
            this.end = schedule.getEnd();
            this.categoryId = schedule.getCategory().getId();
            this.detail = schedule.getDetail();
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

        public ScheduleFindAllDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.categoryId = schedule.getCategory().getId();
            this.start = schedule.getStart();
            this.end = schedule.getEnd();
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
            this.id = schedule.getId();
            this.categoryId = schedule.getCategory().getId();
            this.title = schedule.getTitle();
            this.completion = schedule.isCompletion();
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

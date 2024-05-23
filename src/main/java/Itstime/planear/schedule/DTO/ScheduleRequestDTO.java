package Itstime.planear.schedule.DTO;

import Itstime.planear.schedule.Domain.Category;
import Itstime.planear.schedule.Domain.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
public class ScheduleRequestDTO {
    @Getter
    public static class ScheduleCreateDTO {
        private String title;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate start;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate end;

        private String category;
        private String detail;
    }

    @Getter
    public static class ScheduleUpdateDTO {
        private String title;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate start;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate end;

        private Category category;
        private String detail;
    }
}

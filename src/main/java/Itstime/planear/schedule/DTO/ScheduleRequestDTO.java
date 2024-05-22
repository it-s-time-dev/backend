package Itstime.planear.schedule.DTO;

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

        private String color;
        private String detail;
    }
}

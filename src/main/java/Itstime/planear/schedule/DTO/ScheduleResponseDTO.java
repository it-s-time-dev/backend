package Itstime.planear.schedule.DTO;

import Itstime.planear.schedule.Domain.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDTO {
    @Getter
    public static class ScheduleCreateDTO {
        private Long id;
        private String title;

        public ScheduleCreateDTO(Schedule schedule) {
            this.id = schedule.getId();
            this.title = schedule.getTitle();

        }
    }
}

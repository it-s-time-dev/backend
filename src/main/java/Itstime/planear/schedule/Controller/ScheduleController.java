package Itstime.planear.schedule.Controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.schedule.DTO.ScheduleRequestDTO;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> create(
            @RequestBody ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO,
            @RequestHeader("memberId") Long memberId) throws PlanearException {
        ScheduleResponseDTO.ScheduleCreateDTO result = scheduleService.create(memberId, scheduleCreateDTO);
        return ApiResponse.success(result);
    }
}

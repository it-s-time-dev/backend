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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 추가
    @PostMapping("/schedule")
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> create(
            @RequestBody ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO,
            @RequestHeader("memberId") Long memberId) throws PlanearException {
        ScheduleResponseDTO.ScheduleCreateDTO result = scheduleService.create(memberId, scheduleCreateDTO);
        return ApiResponse.success(result);
    }
    // 일정 수정
    @PutMapping("/schedule/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.ScheduleUpdateDTO> update(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody ScheduleRequestDTO.ScheduleUpdateDTO scheduleUpdateDTO,
            @RequestHeader("memberId") Long memberId) throws PlanearException {
        ScheduleResponseDTO.ScheduleUpdateDTO result = scheduleService.update(memberId,scheduleId,scheduleUpdateDTO);
        return ApiResponse.success(result);
    }
}

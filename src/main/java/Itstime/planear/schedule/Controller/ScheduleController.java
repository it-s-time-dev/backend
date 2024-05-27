package Itstime.planear.schedule.Controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.schedule.DTO.ScheduleRequestDTO;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 추가
    @PostMapping("/schedule")
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> create(
            @RequestBody ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO,
            @RequestHeader(value = "user-no",required = false) Long memberId) {
        ScheduleResponseDTO.ScheduleCreateDTO result = scheduleService.create(memberId,scheduleCreateDTO);
        return ApiResponse.success(result);
    }
    // 일정 수정
    @PutMapping("/schedule/{scheduleId}")
    public ApiResponse<ScheduleResponseDTO.scheduleUpdateDTO> update(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody ScheduleRequestDTO.scheduleUpdateDTO scheduleUpdateDTO,
            @RequestHeader(value = "user-no",required = false) Long memberId) {
        ScheduleResponseDTO.scheduleUpdateDTO result = scheduleService.update(memberId,scheduleId,scheduleUpdateDTO);
        return ApiResponse.success(result);
    }
}

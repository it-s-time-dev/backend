package Itstime.planear.schedule.Controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.schedule.DTO.ScheduleRequestDTO;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public ApiResponse<ScheduleResponseDTO.ScheduleUpdateDTO> update(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody ScheduleRequestDTO.ScheduleUpdateDTO scheduleUpdateDTO,
            @RequestHeader(value = "user-no",required = false) Long memberId) {
        ScheduleResponseDTO.ScheduleUpdateDTO result = scheduleService.update(memberId,scheduleId,scheduleUpdateDTO);
        return ApiResponse.success(result);
    }
    // 일정 완료
    @PostMapping("/schedule/complete")
    public ApiResponse<ScheduleResponseDTO.scheduleCompleteDTO> complete(@RequestBody ScheduleRequestDTO.ScheduleCompleteDTO scheduleCompleteDTO,
            @RequestHeader(value = "user-no",required = false) Long memberId){
                ScheduleResponseDTO.scheduleCompleteDTO result = scheduleService.complete(memberId, scheduleCompleteDTO.getScheduleId());
                return ApiResponse.success(result);
    }
    // 먼슬리 일정 조회
    @GetMapping("/schedule")
    public ApiResponse<List<ScheduleResponseDTO.ScheduleFindAllDTO>> findAll(
            @RequestHeader(value = "user-no", required = false) Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startInclusive,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endInclusive) {

        List<ScheduleResponseDTO.ScheduleFindAllDTO> result = scheduleService.findAll(memberId, startInclusive, endInclusive);
        return ApiResponse.success(result);
    }
    // 상세 일정 조회
    @GetMapping("/schedule/detail")
    public ApiResponse<List<ScheduleResponseDTO.ScheduleFindOneDTO>> findOne(
            @RequestHeader(value = "user-no", required = false) Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDay) {

        List<ScheduleResponseDTO.ScheduleFindOneDTO> result = scheduleService.findOne(memberId, targetDay);
        return ApiResponse.success(result);
    }
}

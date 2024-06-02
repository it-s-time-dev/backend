package Itstime.planear.schedule.Controller;

import Itstime.planear.common.ApiResponse;
import Itstime.planear.schedule.DTO.ScheduleRequestDTO;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "스케줄 컨트롤러", description = "스케줄 관련 API입니다.")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    @Operation(summary = "일정 추가", description = "일정 추가 API")
    public ApiResponse<ScheduleResponseDTO.ScheduleCreateDTO> create(
            @RequestBody ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO,
            @RequestHeader(value = "user-no",required = false) Long memberId) {
        ScheduleResponseDTO.ScheduleCreateDTO result = scheduleService.create(memberId,scheduleCreateDTO);
        return ApiResponse.success(result);
    }
    @PutMapping("/schedule/{scheduleId}")
    @Operation(summary = "일정 수정", description = "일정 관련 title,detail,categoryId,start,end 수정 가능 API")
    public ApiResponse<ScheduleResponseDTO.ScheduleUpdateDTO> update(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody ScheduleRequestDTO.ScheduleUpdateDTO scheduleUpdateDTO,
            @RequestHeader(value = "user-no",required = false) Long memberId) {
        ScheduleResponseDTO.ScheduleUpdateDTO result = scheduleService.update(memberId,scheduleId,scheduleUpdateDTO);
        return ApiResponse.success(result);
    }
    @PostMapping("/schedule/complete")
    @Operation(summary = "일정 완료", description = "일정 완료시 토큰 발행 API")
    public ApiResponse<ScheduleResponseDTO.scheduleCompleteDTO> complete(@RequestBody ScheduleRequestDTO.ScheduleCompleteDTO scheduleCompleteDTO,
            @RequestHeader(value = "user-no",required = false) Long memberId){
                ScheduleResponseDTO.scheduleCompleteDTO result = scheduleService.complete(memberId, scheduleCompleteDTO.getScheduleId());
                return ApiResponse.success(result);
    }
    @GetMapping("/schedule")
    @Operation(summary = "먼슬리 일정 조회", description = "startInclusive,endInclusive통해 원하는 기간 내 일정 조회 API")
    public ApiResponse<List<ScheduleResponseDTO.ScheduleFindAllDTO>> findAll(
            @RequestHeader(value = "user-no", required = false) Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startInclusive,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endInclusive) {

        List<ScheduleResponseDTO.ScheduleFindAllDTO> result = scheduleService.findAll(memberId, startInclusive, endInclusive);
        return ApiResponse.success(result);
    }
    @GetMapping("/schedule/detail")
    @Operation(summary = "하루 상세 일정 조회", description = "targetDay로 선택한 날 상세 일정 조회 API")
    public ApiResponse<List<ScheduleResponseDTO.ScheduleFindOneDTO>> findOne(
            @RequestHeader(value = "user-no", required = false) Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDay) {

        List<ScheduleResponseDTO.ScheduleFindOneDTO> result = scheduleService.findOne(memberId, targetDay);
        return ApiResponse.success(result);
    }
}

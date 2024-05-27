package Itstime.planear.schedule.Controller;

import Itstime.planear.common.ApiResponse;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<?> create(@RequestBody ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO, HttpSession httpSession) {
        try {
            log.info("[ScheduleController] create");
            Long memberId = (Long) httpSession.getAttribute("memberId");
            ScheduleResponseDTO.ScheduleCreateDTO result = scheduleService.create(memberId,scheduleCreateDTO);
            return ResponseEntity.ok().body(ApiResponse.success(result));
        } catch (Exception e) {
            log.info("[ERROR] Exception");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요"));
        }
    }

}

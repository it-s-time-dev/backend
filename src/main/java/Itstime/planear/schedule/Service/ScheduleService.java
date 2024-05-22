package Itstime.planear.schedule.Service;

import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.schedule.DTO.ScheduleRequestDTO;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Domain.Schedule;
import Itstime.planear.schedule.Domain.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    // 일정 추가
    @Transactional
    public ScheduleResponseDTO.ScheduleCreateDTO create(Long memberId, ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO) {
        try {
            log.info("[ScheduleService] create");
            Member findMember = memberRepository.findById(memberId)
                    .orElseThrow(() -> new PlanearException("Member not found", HttpStatus.NOT_FOUND));

            Schedule schedule = new Schedule(scheduleCreateDTO.getTitle(),findMember);
            scheduleRepository.save(schedule);

            return new ScheduleResponseDTO.ScheduleCreateDTO(schedule);
        } catch (PlanearException ce) {
            log.info("[PlanearException] ScheduleService create");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception] ScheduleService create");
            throw new PlanearException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

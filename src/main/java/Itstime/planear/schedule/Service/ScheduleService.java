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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    // 일정 추가
    @Transactional
    public ScheduleResponseDTO.ScheduleCreateDTO create(Long memberId, ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));

        Schedule schedule = new Schedule(scheduleCreateDTO.getTitle(), findMember);
        scheduleRepository.save(schedule);

        return new ScheduleResponseDTO.ScheduleCreateDTO(schedule);
    }
    // 일정 수정
    @Transactional
    public ScheduleResponseDTO.ScheduleUpdateDTO update(Long memberId,Long scheduleId,ScheduleRequestDTO.ScheduleUpdateDTO scheduleUpdateDTO) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요",HttpStatus.NOT_FOUND));
        // map 사용하는게 더 낫나..?
        findSchedule.UpdateTitle(scheduleUpdateDTO.getTitle());
        findSchedule.UpdateDetail(scheduleUpdateDTO.getDetail());
        findSchedule.UpdateStart(scheduleUpdateDTO.getStart());
        findSchedule.UpdateEnd(scheduleUpdateDTO.getEnd());
        findSchedule.UpdateCategory(scheduleUpdateDTO.getCategory());

        return new ScheduleResponseDTO.ScheduleUpdateDTO(findSchedule);
    }



}

package Itstime.planear.schedule.Service;

import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import Itstime.planear.member.domain.MemberRepository;
import Itstime.planear.schedule.DTO.ScheduleRequestDTO;
import Itstime.planear.schedule.DTO.ScheduleResponseDTO;
import Itstime.planear.schedule.Domain.Category;
import Itstime.planear.schedule.Domain.CategoryRepository;
import Itstime.planear.schedule.Domain.Schedule;
import Itstime.planear.schedule.Domain.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    // 일정 추가
    @Transactional
    public ScheduleResponseDTO.ScheduleCreateDTO create(Long memberId, ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));

        Category ChoiceColor = categoryRepository.findById(scheduleCreateDTO.getCategoryId())
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        // null일때 스케줄 추가 날짜로 기본값 설정
        LocalDate start = (scheduleCreateDTO.getStart() != null) ? scheduleCreateDTO.getStart() : LocalDate.now();
        LocalDate end = (scheduleCreateDTO.getEnd() != null) ? scheduleCreateDTO.getEnd() : LocalDate.now();

        Schedule schedule = new Schedule(scheduleCreateDTO.getTitle(), findMember, ChoiceColor, start, end, scheduleCreateDTO.getDetail());

        scheduleRepository.save(schedule);

        return new ScheduleResponseDTO.ScheduleCreateDTO(schedule);
    }

    // 일정 수정
    @Transactional
    public ScheduleResponseDTO.scheduleUpdateDTO update(Long memberId, Long scheduleId, ScheduleRequestDTO.scheduleUpdateDTO scheduleUpdateDTO) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요", HttpStatus.NOT_FOUND));

        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요", HttpStatus.NOT_FOUND));
        // 해당 회원의 스케줄이 맞는지 확인
        checkMemberRelationSchedule(findMember, findSchedule);

        findSchedule.updateTitle(scheduleUpdateDTO.getTitle());
        findSchedule.updateDetail(scheduleUpdateDTO.getDetail());
        findSchedule.updateStart(scheduleUpdateDTO.getStart());
        findSchedule.updateEnd(scheduleUpdateDTO.getEnd());

        // scheduleUpdateDTO에 카테고리 있다면 해당 카테고리로 업데이트하도록
        if (scheduleUpdateDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(scheduleUpdateDTO.getCategoryId())
                    .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요", HttpStatus.NOT_FOUND));
            findSchedule.updateCategory(category);
        }

        return new ScheduleResponseDTO.scheduleUpdateDTO(findSchedule);
    }
    // 일정 완료
    @Transactional
    public ScheduleResponseDTO.scheduleCompleteDTO complete(Long memberId,Long scheduleId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        checkMemberRelationSchedule(findMember,findSchedule);
        findSchedule.updateScheduleStatus(true);

        return new ScheduleResponseDTO.scheduleCompleteDTO(findSchedule);
    }
    private static void checkMemberRelationSchedule(Member findMember, Schedule findSchedule) {
        if (!Objects.equals(findMember.getId(), findSchedule.getMember().getId())) {
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.FORBIDDEN);
        }
    }
}

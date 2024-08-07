package Itstime.planear.schedule.Service;

import Itstime.planear.coin.domain.Coin;
import Itstime.planear.coin.domain.CoinAmount;
import Itstime.planear.coin.domain.CoinRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final CoinRepository coinRepository;

    // 일정 추가
    @Transactional
    public ScheduleResponseDTO.ScheduleCreateDTO create(Long memberId, ScheduleRequestDTO.ScheduleCreateDTO scheduleCreateDTO) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));

        // null일때 스케줄 추가 날짜로 기본값 설정
        LocalDate start = (scheduleCreateDTO.getStart() != null) ? scheduleCreateDTO.getStart() : LocalDate.now();
        LocalDate end = (scheduleCreateDTO.getEnd() != null) ? scheduleCreateDTO.getEnd() : LocalDate.now();
        Long categoryId = scheduleCreateDTO.getCategoryId();

        Schedule schedule = new Schedule(scheduleCreateDTO.getTitle(), findMember, start, end, categoryId ,scheduleCreateDTO.getDetail());

        scheduleRepository.save(schedule);

        return new ScheduleResponseDTO.ScheduleCreateDTO(schedule);
    }

    // 일정 수정
    @Transactional
    public ScheduleResponseDTO.ScheduleUpdateDTO update(Long memberId, Long scheduleId, ScheduleRequestDTO.ScheduleUpdateDTO scheduleUpdateDTO) {
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
        findSchedule.updateCategory(scheduleUpdateDTO.getCategoryId());

        return new ScheduleResponseDTO.ScheduleUpdateDTO(findSchedule);
    }
    // 일정 완료
    @Transactional
    public ScheduleResponseDTO.ScheduleCompleteDTO complete(Long memberId,Long scheduleId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));
        Schedule findSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));
        Coin coin = coinRepository.findByMemberId(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));
        checkMemberRelationSchedule(findMember,findSchedule);
        if (!findSchedule.isCompletion()) { // 일정이미 완료됐는지 확인
            findSchedule.updateScheduleStatus(true); //완료상태로 변경
            CoinAmount resultCoin = coin.getCoinAmount().add(5);
            coin.updateCoinAmount(resultCoin);
            memberRepository.save(findMember);
        }
        return new ScheduleResponseDTO.ScheduleCompleteDTO(findSchedule);
    }
    // 일정 삭제
    @Transactional
    public ScheduleResponseDTO.ScheduleDeleteDTO delete(Long memberId,Long scheduleId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));
        Schedule findSchedule =scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면,연락주세요",HttpStatus.NOT_FOUND));

        checkMemberRelationSchedule(findMember,findSchedule);
        scheduleRepository.deleteById(scheduleId);
        return new ScheduleResponseDTO.ScheduleDeleteDTO(scheduleId);

    }
    // 먼슬리 일정 조회
    public List<ScheduleResponseDTO.ScheduleFindAllDTO> findAll(Long memberId, LocalDate startInclusive, LocalDate endInclusive) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));

        List<Schedule> list = scheduleRepository.findAllByMemberAndStartBetween(findMember, startInclusive, endInclusive);

        List<ScheduleResponseDTO.ScheduleFindAllDTO> scheduleList = new ArrayList<>();
        for (Schedule schedule : list) {
            scheduleList.add(new ScheduleResponseDTO.ScheduleFindAllDTO(schedule)); // 객체 생성해 저장하기
        }
        return scheduleList;
    }
    // 상세 일정 조회
    public List<ScheduleResponseDTO.ScheduleFindOneDTO> findOne(Long memberId, LocalDate targetDay) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.NOT_FOUND));

        LocalDate nextDay = targetDay.plusDays(1); // 다음 날 00:00 전까지
        List<Schedule> list = scheduleRepository.findAllByMemberAndDate(findMember, targetDay, nextDay);
        List<ScheduleResponseDTO.ScheduleFindOneDTO> scheduleList = new ArrayList<>();

        for (Schedule schedule : list) {
            scheduleList.add(new ScheduleResponseDTO.ScheduleFindOneDTO(schedule));
        }
        return scheduleList;
    }

    private static void checkMemberRelationSchedule(Member findMember, Schedule findSchedule) {
        if (!Objects.equals(findMember.getId(), findSchedule.getMember().getId())) {
            throw new PlanearException("잠시 문제가 생겼어요 문제가 반복되면, 연락주세요", HttpStatus.FORBIDDEN);
        }
    }
}

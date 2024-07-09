package Itstime.planear.schedule.Domain;
import java.time.LocalDate;

import Itstime.planear.coin.domain.CoinAmount;
import Itstime.planear.common.BaseEntity;
import Itstime.planear.exception.PlanearException;
import Itstime.planear.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name ="start")
    private LocalDate start;
    @Column(name ="end")
    private LocalDate end;
    @Column(name ="detail")
    private String detail;
    @Column(name ="Completion")
    private boolean completion = false; //초기 상태 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 일대다 관계

    @Column(name = "categoryId")
    private Long categoryId;

    @Embedded
    private CoinAmount coin;


    public Schedule(String title, Member member,LocalDate start,LocalDate end,Long categoryId ,String detail) {
        this.title = title;
        this.member = member;
        this.start = start;
        this.end = end;
        this.detail = detail;
        this.categoryId = categoryId;
        validateDates();
    }
    // 업데이트 관련 메서드
    public void updateTitle(String title ) {
        this.title = title;
    }
    public void updateStart(LocalDate start) {
        this.start = start;
    }
    public void updateEnd(LocalDate end) {
        this.end = end;
    }
    public void updateDetail(String detail ) {
        this.detail = detail;
    }
    public void updateCategory(Long categoryId ) {
        this.categoryId = categoryId;
    }
    // 상태 변경 메서드
    public void updateScheduleStatus(Boolean completion) {
        this.completion = completion != null ? completion : false; // 인자 값 true로 주면 상태변경하도록
    }

    public void validateDates() {
        if (start != null && end != null && start.isAfter(end)) {
            throw new PlanearException("날짜를 다시 선택해 주세요", HttpStatus.BAD_REQUEST);
        }
    }
}

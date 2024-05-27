package Itstime.planear.schedule.Domain;
import java.time.LocalDate;

import Itstime.planear.common.BaseEntity;
import Itstime.planear.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(name ="reward")
    private int reward;
    @Column(name ="Completion")
    private boolean Completion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member; // 일대다 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    public Schedule(String title, Member member,Category category,LocalDate start,LocalDate end,String detail) {
        this.title = title;
        this.member = member;
        this.start = start;
        this.end = end;
        this.category = category;
        this.detail = detail;
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
    public void updateCategory(Category category ) {
        this.category = category;
    }

}

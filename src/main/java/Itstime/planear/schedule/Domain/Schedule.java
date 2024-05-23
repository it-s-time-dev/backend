package Itstime.planear.schedule.Domain;
import java.time.LocalDate;

import Itstime.planear.common.BaseEntity;
import Itstime.planear.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "schedule")
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
    @JoinColumn(name = "member_id")
    private Member member; // 일대다 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    protected Schedule() {

    }
    public Schedule(String title, Member member) {
        this.title = title;
        this.member = member;
    }
    // 업데이트 관련 메서드
    public void UpdateTitle(String title ) {
        this.title = title;
    }
    public void UpdateStart(LocalDate start) {
        this.start = start;
    }
    public void UpdateEnd(LocalDate end) {
        this.end = end;
    }
    public void UpdateDetail(String detail ) {
        this.detail = detail;
    }
    public void UpdateCategory(Category category ) {
        this.category = category;
    }

}

package Itstime.planear.schedule.Domain;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private String title;

    @Column
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String detail;
    private int reward;
    private boolean Completion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 일대다 관계

    protected Schedule() {

    }
    public Schedule(String title, Member member) {
        this.title = title;
        this.member = member;
    }
}

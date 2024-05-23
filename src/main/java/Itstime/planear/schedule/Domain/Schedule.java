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

    @Column(name="title",nullable = false)
    private String title;

    @Column(name ="startDate")
    private LocalDateTime startDate;
    @Column(name ="endDate")
    private LocalDateTime endDate;
    @Column(name ="detail")
    private String detail;
    @Column(name ="reward")
    private int reward;
    @Column(name ="Completion")
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

package Itstime.planear.schedule.Domain;

import Itstime.planear.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "memberId")
    private Long memberId;

}

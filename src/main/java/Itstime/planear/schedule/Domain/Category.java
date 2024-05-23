package Itstime.planear.schedule.Domain;

import Itstime.planear.member.domain.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "catagory")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="color",nullable = false)
    private String color; // 색상 코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}

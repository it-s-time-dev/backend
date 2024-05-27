package Itstime.planear.shop.domain;


import Itstime.planear.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private BodyPart bodyPart; // 부위 (구매내역 중 카테고리 필터링 역할)

    public Purchase(Member member, Item item, BodyPart bodyPart) {
        this.member = member;
        this.item = item;
        this.bodyPart = bodyPart;
    }
}

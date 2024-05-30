package Itstime.planear.shop.domain;


import Itstime.planear.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wearing", uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "bodyPart"}, name = "idx_member_bodyPart")})
public class Wearing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Enumerated(EnumType.STRING)
    @Column(name = "bodyPart", nullable = false)
    private BodyPart bodyPart;

    public Wearing(Member member, Item item, BodyPart bodyPart) {
        this.member = member;
        this.item = item;
        this.bodyPart = bodyPart;
    }

    public void updateWearingItem(Item item){
        this.item = item;
        this.bodyPart = item.getBodyPart();
    }
}

package Itstime.planear.shop.domain;


import Itstime.planear.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "bodyPart", nullable = false)
    private BodyPart bodyPart;

    public Wearing(Member member, Item item, BodyPart bodyPart) {
        this.member = member;
        this.item = item;
        this.bodyPart = bodyPart;
    }

    public void updateWearingItem(Item item) {
        this.item = item;
        this.bodyPart = item.getBodyPart();
    }
}

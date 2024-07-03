package Itstime.planear.member.domain;

import Itstime.planear.coin.domain.CoinAmount;
import Itstime.planear.common.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "idx_name")})
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberCode;

    @Embedded
    private MemberName memberName;
    @Embedded
    private CoinAmount coin;

    public Member(String name, String memberCode) {
        memberName = new MemberName(name);
        this.coin = new CoinAmount(0);
        this.memberCode = memberCode;
    }

    public void updateCoinAmount(CoinAmount coinAmount){
        this.coin = coinAmount;
    }
}

package Itstime.planear.member.domain;

import Itstime.planear.common.BaseEntity;
import Itstime.planear.shop.domain.Wearing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

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

    public Member(String name, String memberCode) {
        memberName = new MemberName(name);
        this.memberCode = memberCode;
    }
}

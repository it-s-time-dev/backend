package Itstime.planear.friend.domain;

import Itstime.planear.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "friend_member_id")
    private Member friendMember;

    @Column(name = "friend_member_id", insertable = false, updatable = false)
    private Long friendMemberId;

    public Friend(Member member, Member friendMember) {
        this.friendMember = friendMember;
        this.member = member;
    }
}

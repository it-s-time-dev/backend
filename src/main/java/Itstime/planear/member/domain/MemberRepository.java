package Itstime.planear.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberName(MemberName memberName);
}

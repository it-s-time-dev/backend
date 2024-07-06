package Itstime.planear.statusmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberQuestionRepository extends JpaRepository<MemberQuestion, Long> {

    Optional<MemberQuestion> findFirstByMemberIdAndOrderByIdDesc(Long memberId);
}

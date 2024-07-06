package Itstime.planear.statusmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusMessageRepository extends JpaRepository<StatusMessage, Long> {

    Optional<StatusMessage> findByMemberId(Long memberId);

    Optional<StatusMessage> findByMemberIdOrderByIdDesc(Long memberId);
}

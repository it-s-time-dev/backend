package Itstime.planear.statusmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StatusMessageRepository extends JpaRepository<StatusMessage, Long> {

    Optional<StatusMessage> findFirstByMemberIdOrderByIdDesc(Long memberId);

    List<StatusMessage> findAllByMemberIdInAndCreatedAtBetweenOrderByIdDesc(List<Long> memberId, LocalDateTime startDate, LocalDateTime endDate);
}

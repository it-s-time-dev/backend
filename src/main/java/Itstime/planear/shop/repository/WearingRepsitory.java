package Itstime.planear.shop.repository;

import Itstime.planear.shop.domain.BodyPart;
import Itstime.planear.shop.domain.Wearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WearingRepsitory extends JpaRepository<Wearing, Long> {

    List<Wearing> findByMemberId(Long memberId);

    Optional<Wearing> findByMemberIdAndBodyPart(Long memberId, BodyPart bodyPart);

    List<Wearing> findAllByMemberIdIn(List<Long> memberIds);
}

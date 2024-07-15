package Itstime.planear.shop.repository;

import Itstime.planear.shop.domain.BodyPart;
import Itstime.planear.shop.domain.Wearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WearingRepsitory extends JpaRepository<Wearing, Long> {

    List<Wearing> findByMemberId(Long memberId);

    Optional<Wearing> findByMemberIdAndBodyPart(Long memberId, BodyPart bodyPart);
    List<Wearing> findAllByMemberId(Long memberId);

    List<Wearing> findAllByMemberIdIn(List<Long> memberIds);

    // 멤버id 리스트로 조회
    @Query("SELECT w FROM Wearing w JOIN FETCH w.item WHERE w.member.id IN :memberIds")
    List<Wearing> findByMemberIds(@Param("memberIds") List<Long> memberIds);
}

package Itstime.planear.shop.repository;

import Itstime.planear.shop.domain.Item;
import Itstime.planear.shop.domain.Wearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WearingRepsitory extends JpaRepository<Wearing, Long> {

    boolean existsByMemberIdAndItem(Long memberId, Item item);
    List<Wearing> findByMemberId(Long memberId);
}

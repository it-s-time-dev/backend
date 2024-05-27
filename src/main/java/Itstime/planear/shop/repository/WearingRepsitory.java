package Itstime.planear.shop.repository;

import Itstime.planear.shop.domain.Item;
import Itstime.planear.shop.domain.Wearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WearingRepsitory extends JpaRepository<Wearing, Long> {

    boolean existsByMemberIdAndItem(Long memberId, Item item);
}
